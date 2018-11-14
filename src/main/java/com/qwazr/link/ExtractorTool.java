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

import java.io.IOException;

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

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("The path to the file") String path) {
        return getExtractorService().putMagic(null, null, path, null, null);
    }

    @Component("Extract text and metadata")
    public ParserResult extract(@Component("A crawled content") DriverInterface.Content content) throws IOException {
        return getExtractorService().putMagic(null, null, null, content.getContentType(), content.getInput());
    }
}
