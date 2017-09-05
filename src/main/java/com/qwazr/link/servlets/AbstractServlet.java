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
import com.qwazr.utils.LoggerUtils;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ekeller on 05/07/2017.
 */
abstract class AbstractServlet extends HttpServlet {

	final FreeMarkerTool freeMarkerTool;

	private final static Logger LOGGER = LoggerUtils.getLogger(AbstractServlet.class);

	AbstractServlet(final FreeMarkerTool freeMarkerTool) {
		this.freeMarkerTool = freeMarkerTool;
	}

	final protected void template(final String templatePath, final Transaction transaction)
			throws ServletException, IOException {
		try {
			freeMarkerTool.template(templatePath, transaction.dataModel, transaction.response);
		} catch (TemplateException e) {
			transaction.response.sendError(500, e.getMessage());
		}
	}

	protected void doGet(Transaction transaction) throws ServletException, IOException {
		super.doGet(transaction.request, transaction.response);
	}

	@FunctionalInterface
	public interface DoMethod {
		void doMethod(Transaction transaction) throws ServletException, IOException;
	}

	private void logger(final Transaction transaction, final DoMethod method) throws ServletException, IOException {
		try {
			method.doMethod(transaction);
		} catch (LinkException e) {
			LOGGER.log(Level.WARNING, e, e::getMessage);
			if (transaction.response.isCommitted())
				throw e;
			else
				e.sendError(transaction.response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e, e::getMessage);
			throw e;
		}
	}

	@Override
	final protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger(new Transaction(request, response), this::doGet);
	}

	protected void doPost(Transaction transaction) throws ServletException, IOException {
		super.doPost(transaction.request, transaction.response);
	}

	@Override
	final protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger(new Transaction(request, response), this::doPost);
	}
}
