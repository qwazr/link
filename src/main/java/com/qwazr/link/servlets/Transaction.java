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

import com.qwazr.utils.LoggerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ekeller on 06/07/2017.
 */
class Transaction {

	private final static Logger LOGGER = LoggerUtils.getLogger(Transaction.class);

	final HttpServletRequest request;

	final HttpServletResponse response;

	final Map<String, Object> dataModel;

	final SessionWrapper session;

	Transaction(final HttpServletRequest request, final HttpServletResponse response) {
		this.request = request;
		this.response = response;
		session = new SessionWrapper(request);
		dataModel = new HashMap<>();
		dataModel.put("request", request);
		dataModel.put("session", session);
	}

	String getRequestParameter(String name) {
		return request.getParameter(name);
	}

	Integer getRequestParameter(String name, Integer defaultValue) {
		final String value = getRequestParameter(name);
		return value == null ? defaultValue : Integer.parseInt(value);
	}

	void addMessage(Messages.Type type, String title, String message, boolean close) {
		session.getMessages().add(type, title, message, close);
	}

	void addMessage(Throwable throwable) {
		LOGGER.log(Level.SEVERE, throwable, throwable::getMessage);
		final String title = throwable instanceof LinkException ?
				((LinkException) throwable).getTitle() :
				throwable.getClass().getName();
		session.getMessages().add(Messages.Type.danger, title, throwable.getMessage(), true);
	}
}
