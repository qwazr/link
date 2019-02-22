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

import com.qwazr.component.ComponentInterface;
import com.qwazr.component.annotations.Component;
import com.qwazr.crawler.web.driver.DriverInterface;
import com.qwazr.extractor.ExtractorManager;
import com.qwazr.extractor.ExtractorServiceInterface;
import com.qwazr.extractor.ParserResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Component("Text and metadata extractor")
public class ExtractorTool implements ComponentInterface {

    private static volatile ExtractorServiceInterface extractorService;

    private static ExtractorServiceInterface getExtractorService() {
        if (extractorService != null)
            return extractorService;
        synchronized (ExtractorTool.class) {
            if (extractorService != null)
                return extractorService;
            return extractorService = new ExtractorManager().registerServices().getService();
        }
    }

    private MultivaluedMap<String, String> getMultivaluedMap(final Map<String, Object> parameters) {
        if (parameters == null || parameters.isEmpty())
            return null;
        final MultivaluedMap<String, String> multivaluedMap = new MultivaluedHashMap<>();
        parameters.forEach((key, value) -> {
            if (value instanceof Collection) {
                final Collection<?> collection = (Collection) value;
                collection.forEach(val -> multivaluedMap.add(key, val.toString()));
            } else
                multivaluedMap.add(key, value.toString());
        });
        return multivaluedMap;
    }

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("The path to the file") final String path,
                                @Component("Optional parameters passed to the parser") final Map<String, Object> parameters) {
        return getExtractorService().extractMagic(getMultivaluedMap(parameters), null, path, null, null);
    }

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("The path to the file") final String path) {
        return extract(path, null);
    }

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("A crawled content") final DriverInterface.Content content,
                                @Component("Optional parameters passed to the parser") final Map<String, Object> parameters)
            throws IOException {
        return getExtractorService().extractMagic(getMultivaluedMap(parameters), null, null,
                content.getContentType(), content.getInput());
    }

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("A crawled content") final DriverInterface.Content content)
            throws IOException {
        return extract(content, null);
    }

}
