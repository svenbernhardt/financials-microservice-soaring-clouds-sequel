package com.soaringclouds.webshop.financialsmicroservice;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;

import io.helidon.config.Config;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.helidon.config.ConfigSources.classpath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class ConverterTest {

    @Test
    public void test() throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/example.json"));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        final Invoice invoice = mapper.readValue(bytes, Invoice.class);

        System.out.println(String.format("The invoice object: %s", invoice));

        final String invoiceAsString = mapper.writeValueAsString(invoice);

        System.out.println(String.format("The invoice object as String: %s", invoiceAsString));
    }

    @Test
    public void testConfig() {

        Config config = Config.create(classpath("application.yaml"));

        String host = config.get("persistence.host").asString().get();

        assertThat(host, notNullValue());
    }
}
