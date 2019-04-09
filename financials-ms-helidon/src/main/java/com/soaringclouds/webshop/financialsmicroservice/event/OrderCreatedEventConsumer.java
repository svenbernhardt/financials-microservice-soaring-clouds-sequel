package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OrderCreatedEventConsumer extends RouteBuilder {

    @Inject
    private KafkaConfig kafkaConfig;

    @Override
    public void configure() throws Exception {
        from(KafkaConusmerEndpointBuilder.createAvroConsumerForTopic(kafkaConfig,
                kafkaConfig.getTopicOrderCreated()).build()).log("Order message " +
                "received: ${body}").to("bean:ordercreatedeventhandler");
    }
}
