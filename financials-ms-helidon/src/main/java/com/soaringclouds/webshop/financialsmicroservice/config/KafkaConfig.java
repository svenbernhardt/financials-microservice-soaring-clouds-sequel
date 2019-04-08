package com.soaringclouds.webshop.financialsmicroservice.config;

import org.apache.camel.component.kafka.KafkaEndpoint;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class KafkaConfig {

    @Inject
    @ConfigProperty(name = "app.kafka.bootstrap-servers")
    private String bootstrapServer;

    @Inject
    @ConfigProperty(name = "app.kafka.schema-registry-url")
    private String schemaRegistryUrl;

    @Inject
    @ConfigProperty(name = "app.kafka.topic.customerstatus")
    private String topicCustomerStatus;

    @Inject
    @ConfigProperty(name = "app.kafka.topic.paymentstatus")
    private String topicPaymentStatus;

    @Inject
    @ConfigProperty(name = "app.kafka.topic.ordercreated")
    private String topicOrderCreated;

    @Inject
    @ConfigProperty(name = "app.kafka.topic.shippingnews")
    private String topicShippingNews;

    public KafkaConfig() {
    }

    public KafkaConfig(String pBootstrapServer, String pSchemaRegistryUrl) {

        bootstrapServer = pBootstrapServer;
        schemaRegistryUrl = pSchemaRegistryUrl;
    }

    private Map<String, Object> producerConfig;

    private Map<String, Object> consumerConfig;

    private KafkaEndpoint kafkaEndpointNonAvro;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public String getTopicCustomerStatus() {
        return topicCustomerStatus;
    }

    public String getTopicPaymentStatus() {
        return topicPaymentStatus;
    }

    public String getTopicOrderCreated() {
        return topicOrderCreated;
    }

    public String getTopicShippingNews() {
        return topicShippingNews;
    }
}
