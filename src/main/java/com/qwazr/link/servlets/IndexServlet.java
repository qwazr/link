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
import com.qwazr.scripts.RunThreadAbstract;
import com.qwazr.scripts.ScriptManager;
import com.qwazr.utils.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "index", urlPatterns = "")
public class IndexServlet extends AbstractServlet {

	final ScriptManager scriptManager;

	public IndexServlet(final FreeMarkerTool freemarker, final ScriptManager scriptManager) {
		super(freemarker);
		this.scriptManager = scriptManager;
	}

	@Override
	protected void doGet(final Transaction transaction) throws ServletException, IOException {
		transaction.dataModel.put("linkscript",
				transaction.session.getAttribute(SessionWrapper.Attributes.LINKSCRIPT, String.class));
		template("/com/qwazr/link/front/templates/index.ftl", transaction);
	}

	@Override
	protected void doPost(final Transaction transaction) throws ServletException, IOException {
		final String linkScript = transaction.getRequestParameter("linkscript");
		transaction.session.setAttribute(SessionWrapper.Attributes.LINKSCRIPT, linkScript);
		final Path scriptPath = Files.createTempFile("qwazr-link", ".js");
		try {
			IOUtils.writeStringAsFile(linkScript, scriptPath.toFile());
			final RunThreadAbstract run = scriptManager.getService().runSync(scriptPath.toString(), null);
			final Exception e = run.getException();
			if (e != null)
				transaction.addMessage(e);
			transaction.dataModel.put("scriptout", run.getOut());
		} catch (ClassNotFoundException e) {
			transaction.addMessage(e);
		} finally {
			Files.deleteIfExists(scriptPath);
		}
		doGet(transaction);
	}

}
