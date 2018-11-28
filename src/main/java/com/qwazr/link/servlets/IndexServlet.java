/*
 * Copyright 2015-2017 Emmanuel Keller / QWAZR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qwazr.link.servlets;

import com.qwazr.library.freemarker.FreeMarkerTool;
import com.qwazr.scripts.ScriptServiceInterface;
import com.qwazr.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "index", urlPatterns = "")
public class IndexServlet extends AbstractServlet {

    private final ScriptServiceInterface scriptService;

    public IndexServlet(final FreeMarkerTool freemarker, final ScriptServiceInterface scriptService) {
        super(freemarker);
        this.scriptService = scriptService;
    }

    @Override
    protected void doGet(final Transaction transaction) throws ServletException, IOException {
        transaction.dataModel.put("scripts", scriptService.getRunsStatus());
        template("/com/qwazr/link/front/templates/index.ftl", transaction);
    }

    @Override
    protected void doPost(final Transaction transaction) throws ServletException, IOException {
        final String scriptPath = transaction.getRequestParameter("scriptPath");
        transaction.dataModel.put("scriptPath", scriptPath);
        if (StringUtils.isBlank(scriptPath)) {
            transaction.addMessage(Messages.Type.danger, "Error", "The script path is empty.", true);
        } else {
            try {
                scriptService.runAsync(scriptPath, null);
            } catch (ClassNotFoundException e) {
                transaction.addMessage(e);
            }
        }
        doGet(transaction);
    }

}
