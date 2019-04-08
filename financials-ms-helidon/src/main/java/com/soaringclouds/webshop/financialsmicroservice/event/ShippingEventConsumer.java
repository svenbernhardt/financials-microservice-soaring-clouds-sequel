package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShippingEventConsumer extends RouteBuilder {

    @Inject
    private KafkaConfig kafkaConfig;

    @Override
    public void configure() throws Exception {

        from(new KafkaConusmerEndpointBuilder(kafkaConfig).forTopic(kafkaConfig.getTopicShippingNews()).withGroupId("financials-helidon")
                .withKeyDeserializer(StringDeserializer.class).withValueDeserializer(StringDeserializer.class).build()
        ).log("Received message from topic: ${body}");
    }
}
