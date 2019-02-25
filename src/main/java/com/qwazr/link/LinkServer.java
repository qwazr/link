/*
 * Copyright 2015-2017 Emmanuel Keller / QWAZR
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

import com.qwazr.component.ComponentsManager;
import com.qwazr.library.freemarker.FreeMarkerTool;
import com.qwazr.link.servlets.EditorServlet;
import com.qwazr.link.servlets.IndexServlet;
import com.qwazr.link.servlets.JobsServlet;
import com.qwazr.link.servlets.LibraryServlet;
import com.qwazr.link.servlets.StdErrServlet;
import com.qwazr.link.servlets.StdOutServlet;
import com.qwazr.scripts.ScriptManager;
import com.qwazr.server.BaseServer;
import com.qwazr.server.GenericServer;
import com.qwazr.server.GenericServerBuilder;
import com.qwazr.server.configuration.ServerConfiguration;
import com.qwazr.webapps.WebappManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LinkServer implements BaseServer {

    private final GenericServer server;

    private final ExecutorService executorService;

    private final ScriptManager scriptManager;

    private LinkServer(final ServerConfiguration configuration) throws IOException, ReflectiveOperationException {

        executorService = Executors.newCachedThreadPool();

        final GenericServerBuilder builder = GenericServer.of(configuration, executorService);

        final ComponentsManager componentManager = new ComponentsManager().registerServices();

        final FreeMarkerTool freemarkerResources = FreeMarkerTool.of().useClassloader(true).build();
        freemarkerResources.load();

        scriptManager = new ScriptManager(executorService, null);

        final WebappManager.Builder webappBuilder = WebappManager.of(builder, builder.getWebAppContext());

        webappBuilder.registerDefaultFaviconServlet().registerWebjars();
        webappBuilder.registerJavaServlet(IndexServlet.class,
                () -> new IndexServlet(freemarkerResources, scriptManager.getService()));
        webappBuilder.registerJavaServlet(JobsServlet.class, () -> new JobsServlet(freemarkerResources));
        webappBuilder.registerJavaServlet(LibraryServlet.class,
                () -> new LibraryServlet(freemarkerResources, componentManager));
        webappBuilder.registerJavaServlet(EditorServlet.class, () -> new EditorServlet(freemarkerResources));
        webappBuilder.registerJavaServlet(StdOutServlet.class, () -> new StdOutServlet(freemarkerResources,
                scriptManager.getService()));
        webappBuilder.registerJavaServlet(StdErrServlet.class, () -> new StdErrServlet(freemarkerResources,
                scriptManager.getService()));
        webappBuilder.registerStaticServlet("/s/*", "com.qwazr.link.front.statics");
        webappBuilder.build();
        //webappManager.registerJaxRsResources(
        //		ApplicationBuilder.of("/api/*").singletons(new ApiService(reposDirectory, indexService)), ctx);

        server = builder.build();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    @Override
    public GenericServer getServer() {
        return server;
    }

    private static LinkServer INSTANCE;

    public static LinkServer getInstance() {
        return INSTANCE;
    }

    public static synchronized void main(final String... args) throws Exception {
        if (INSTANCE != null)
            throw new RuntimeException("The server is already started");
        INSTANCE = new LinkServer(new ServerConfiguration(args));
        INSTANCE.start();
    }

    public static synchronized void shutdown() {
        if (INSTANCE == null)
            return;
        INSTANCE.server.close();
        INSTANCE = null;
    }

}
