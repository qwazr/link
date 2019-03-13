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
import com.qwazr.crawler.web.WebCrawlDefinition;
import com.qwazr.crawler.web.WebCrawlStatus;
import com.qwazr.crawler.web.WebCrawlerManager;
import com.qwazr.crawler.web.WebCrawlerServiceInterface;
import com.qwazr.crawler.web.driver.DriverInterface;
import com.qwazr.scripts.ScriptUtils;
import org.graalvm.polyglot.Value;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component("Web crawler")
public class WebCrawlerTool implements ComponentInterface {

    private static volatile WebCrawlerServiceInterface webCrawlerService;

    private static WebCrawlerServiceInterface getWebCrawlerService() {
        if (webCrawlerService != null)
            return webCrawlerService;
        final LinkServer linkServer = LinkServer.getInstance();
        synchronized (WebCrawlerTool.class) {
            if (webCrawlerService == null)
                webCrawlerService = new WebCrawlerManager((String) null, linkServer.getScriptManager(),
                        linkServer.getExecutorService()).getService();
            return webCrawlerService;
        }
    }

    @Component("Start a crawl session")
    public WebCrawlStatus crawl(@Component("The crawl ID") String crawlId,
                                @Component("The crawl definition") Value crawlDefinition)
            throws IOException {
        final WebCrawlDefinition webCrawlDefinition = ScriptUtils.fromJson(crawlDefinition, WebCrawlDefinition.class);
        return getWebCrawlerService().runSession(crawlId, webCrawlDefinition);
    }

    @Component("Save the crawled content to a file")
    public void save(@Component("The crawled content") DriverInterface.Content content,
                     @Component("The path of the file") String path) throws IOException {
        final Path destPath = Path.of(path);
        try (final InputStream input = content.getInput()) {
            Files.copy(input, destPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Cannot write the file " + destPath.toAbsolutePath() + " - " + e.getMessage(), e);
        }
    }

}



