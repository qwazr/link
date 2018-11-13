/*
 * Copyright 2015-2018 Emmanuel Keller / QWAZR
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qwazr.link;

import com.qwazr.utils.ObjectMappers;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.io.IOException;

public class LinkUtils {

    public static <T> T convert(final ScriptObjectMirror source, final Class<T> jsonClass) throws IOException {
        final ScriptObjectMirror json = (ScriptObjectMirror) source.eval("JSON");
        return ObjectMappers.JSON.readValue(json.callMember("stringify", source).toString(), jsonClass);

    }
}
