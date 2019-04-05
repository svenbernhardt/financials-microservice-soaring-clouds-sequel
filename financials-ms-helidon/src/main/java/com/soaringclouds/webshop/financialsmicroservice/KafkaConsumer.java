package com.soaringclouds.webshop.financialsmicroservice;

import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaConsumer extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getSimpleName());

    @Inject
    private KafkaConfig kafkaConfig;

    @Inject
    private InvoiceService invoiceService;

    @Override
    public void configure() throws Exception {
        LOGGER.info(String.format("Entered PostConstruct method..."));

        from("kafka:soaring-shippingnews?brokers=kafka:9092"
                + "&maxPollRecords=1"
                + "&consumersCount=1"
                + "&groupId=financials-helidon"
                + "&serializerClass=kafka.serializer.StringEncoder"
                + "&keyDeserializer=org.apache.kafka.common.serialization.StringDeserializer"
                + "&valueDeserializer=org.apache.kafka.common.serialization.StringDeserializer"
        ).log("Receveived message from Kafka: ${body}").bean(invoiceService,
                "updateInvoiceWithShippingInformation(com.soaringclouds.webshop" +
                        ".financialsmicroservice.model.ShippingEvent)");


    }
}
