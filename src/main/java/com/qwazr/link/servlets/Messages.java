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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ekeller on 06/07/2017.
 */
public class Messages {

    private final Set<Message> messages = new LinkedHashSet<>();

    public enum Type {
        success, info, warning, danger
    }

    public void add(Type type, String title, String message, boolean close) {
        synchronized (messages) {
            messages.add(new Message(type, title, message, close));
        }
    }

    public List<Message> getList() {
        synchronized (messages) {
            if (messages.isEmpty())
                return Collections.emptyList();
            final List<Message> msgs = new ArrayList<>(messages);
            messages.clear();
            return msgs;
        }
    }

    public static class Message {

        final Type type;
        final String title;
        final String content;
        final boolean close;

        private Message(Type type, String title, String content, boolean close) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.close = close;
        }

        public String getType() {
            return type.name();
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public boolean isClose() {
            return close;
        }
    }
}
