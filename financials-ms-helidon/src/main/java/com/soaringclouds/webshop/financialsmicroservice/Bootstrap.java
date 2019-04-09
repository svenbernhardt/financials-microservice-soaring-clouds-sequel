/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.soaringclouds.webshop.financialsmicroservice;

import io.helidon.config.Config;
import io.helidon.microprofile.server.Server;

import java.io.IOException;
import java.util.logging.LogManager;

import static io.helidon.config.ConfigSources.classpath;

/**
 * Bootstrap method simulating trigger of main method of the server.
 */
public final class Bootstrap {

    /**
     * Cannot be instantiated.
     */
    private Bootstrap() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        setupLogging();
        startServer();
    }

    /**
     * Start the server.
     *
     * @return the created {@link Server} instance
     */
    static Server startServer() {

        return Server.builder().config(Config.create(classpath("application.yaml"))).build().start();
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        // load logging configuration
        LogManager.getLogManager().readConfiguration(
                Bootstrap.class.getResourceAsStream("/logging.properties"));
    }
}
