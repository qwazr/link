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
import com.qwazr.utils.IOUtils;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "out", urlPatterns = "/out")
public class StdOutServlet extends AbstractServlet {

    private final ScriptServiceInterface scriptService;

    public StdOutServlet(final FreeMarkerTool freemarker, final ScriptServiceInterface scriptService) {
        super(freemarker);
        this.scriptService = scriptService;
    }

    @Override
    protected void doGet(final Transaction transaction) throws IOException {
        transaction.response.setContentType("text/plain");
        transaction.response.setCharacterEncoding("UTF-8");
        try (final InputStream inputStream = scriptService.getRunOut(transaction.getRequestParameter("id"))) {
            IOUtils.copy(inputStream, transaction.response.getOutputStream());
        }
    }

}
