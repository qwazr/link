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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "jobs", urlPatterns = "/jobs")
public class JobsServlet extends AbstractServlet {

	public JobsServlet(final FreeMarkerTool freemarker) {
		super(freemarker);
	}

	@Override
	protected void doGet(final Transaction transaction) throws ServletException, IOException {
		template("/com/qwazr/link/front/templates/jobs.ftl", transaction);
	}

}
