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

import com.qwazr.utils.IOUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;

public class CrawlerToolTest {

    private final static Path SCRIPT = Paths.get("src", "test", "webcrawlertool-script.js");

    @BeforeClass
    public static void setup() throws Exception {
        LinkServer.main();
    }

    @AfterClass
    public static void cleanup() {
        LinkServer.shutdown();
    }

    @Test
    public void test() {
        final Client client = ClientBuilder.newClient();
        try {
            try (final Response response = client
                    .target("http://localhost:9090")
                    .request()
                    .post(Entity.form(new Form().param("linkscript", IOUtils.readPathAsString(SCRIPT, StandardCharsets.UTF_8))))) {
                Assert.assertThat(response.readEntity(String.class), response.getStatus(), equalTo(200));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            client.close();
        }
    }
}
