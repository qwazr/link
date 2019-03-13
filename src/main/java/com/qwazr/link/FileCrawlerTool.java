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
import com.qwazr.crawler.file.FileCrawlDefinition;
import com.qwazr.crawler.file.FileCrawlStatus;
import com.qwazr.crawler.file.FileCrawlerManager;
import com.qwazr.crawler.file.FileCrawlerServiceInterface;
import com.qwazr.scripts.ScriptUtils;
import org.graalvm.polyglot.Value;

import java.io.IOException;

@Component("File crawler")
public class FileCrawlerTool implements ComponentInterface {

    private static volatile FileCrawlerServiceInterface fileCrawlerService;

    private static FileCrawlerServiceInterface getFileCrawlerService() {
        if (fileCrawlerService != null)
            return fileCrawlerService;
        final LinkServer linkServer = LinkServer.getInstance();
        synchronized (FileCrawlerServiceInterface.class) {
            if (fileCrawlerService == null)
                fileCrawlerService = new FileCrawlerManager((String) null, linkServer.getScriptManager(),
                        linkServer.getExecutorService()).getService();
            return fileCrawlerService;
        }
    }

    @Component("Start a crawl session")
    public FileCrawlStatus crawl(@Component("The crawl ID") String crawlId,
                                 @Component("The crawl definition") Value crawlDefinition)
            throws IOException {
        final FileCrawlDefinition fileCrawlDefinition = ScriptUtils.fromJson(crawlDefinition, FileCrawlDefinition.class);
        return getFileCrawlerService().runSession(crawlId, fileCrawlDefinition);
    }

}



