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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.CustomerAccounts;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.Invoices;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.Payments;
import io.helidon.config.Config;
import io.helidon.microprofile.server.JaxRsApplication;
import io.helidon.microprofile.server.Server;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;

import static io.helidon.config.ConfigSources.classpath;

/**
 * Main method simulating trigger of main method of the server.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
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

        final JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(ObjectMapperProvider.getCustomObjectMapper());

        final ResourceConfig financialsMicroserviceResourceConfig = new ResourceConfig();
        financialsMicroserviceResourceConfig.register(Invoices.class);
        financialsMicroserviceResourceConfig.register(CustomerAccounts.class);
        financialsMicroserviceResourceConfig.register(Payments.class);
        financialsMicroserviceResourceConfig.register(jacksonProvider);

        final Map<String, Object> jaxrsProperties = new HashMap<>();
        jaxrsProperties.put(CommonProperties.JSON_PROCESSING_FEATURE_DISABLE, Boolean.TRUE);
        jaxrsProperties.put(CommonProperties.MOXY_JSON_FEATURE_DISABLE, Boolean.TRUE);
        financialsMicroserviceResourceConfig.addProperties(jaxrsProperties);

        return Server.builder().addApplication(JaxRsApplication.builder().contextRoot("/api" +
                "/financials").config(financialsMicroserviceResourceConfig).build()).config(Config.create(classpath("META-INF/microprofile-config.properties"))).build().start();
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        // load logging configuration
        LogManager.getLogManager().readConfiguration(
                Main.class.getResourceAsStream("/logging.properties"));
    }
}
