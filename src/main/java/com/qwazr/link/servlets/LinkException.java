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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LinkException extends RuntimeException {

	private final String title;

	public LinkException(String title, String message, Throwable cause) {
		super(message, cause);
		this.title = title;
	}

	String getTitle() {
		return title;
	}

	void addMessage(final Transaction transaction) {
		transaction.addMessage(Messages.Type.danger, title, getMessage(), true);
	}

	void sendError(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setStatus(500);
		final PrintWriter writer = response.getWriter();
		writer.println("<h3>" + title + "</h3>");
		writer.println("<p>" + getMessage() + "</p>");
	}
}
