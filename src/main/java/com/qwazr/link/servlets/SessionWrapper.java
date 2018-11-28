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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ekeller on 06/07/2017.
 */
public class SessionWrapper {

    enum Attributes {
        MESSAGES
    }

    private final HttpSession session;

    SessionWrapper(HttpServletRequest request) {
        session = request.getSession();
    }

    public void invalidate() {
        session.invalidate();
    }

    <T> void setAttribute(Attributes attr, T value) {
        session.setAttribute(attr.name(), value);
    }

    <T> T getAttribute(Attributes attr, Class<T> tClass) {
        return tClass.cast(session.getAttribute(attr.name()));
    }

    public Messages getMessages() {
        Messages messages = getAttribute(Attributes.MESSAGES, Messages.class);
        if (messages != null)
            return messages;
        messages = new Messages();
        setAttribute(Attributes.MESSAGES, messages);
        return messages;
    }
}
