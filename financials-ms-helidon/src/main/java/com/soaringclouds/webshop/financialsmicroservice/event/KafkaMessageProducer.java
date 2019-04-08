package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.customerstatus.v1.CustomerStatus;
import com.soaringclouds.avro.customerstatus.v1.CustomerStatusEnum;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatusEnum;
import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaMessageProducer {

    private static final Logger LOGGER = Logger.getLogger(KafkaMessageProducer.class.getName());

    @Inject
    private KafkaConfig kafkaConfig;

    private Map<String, Object> producerConfig;

    private Map<String, Object> getProducerConfig() {

        if (Objects.isNull(producerConfig)) {
            producerConfig = new HashMap<>();

            producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    kafkaConfig.getBootstrapServer());
            producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    KafkaAvroSerializer.class);
            producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    KafkaAvroSerializer.class);
            producerConfig.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                    kafkaConfig.getSchemaRegistryUrl());
        }

        return producerConfig;
    }

    public void produceCustomerStatusFrozen(String pCustomerNo) {
        produceCustomerStatus(pCustomerNo, CustomerStatusEnum.FROZEN);
    }

    public void produceCustomerStatusUnFrozen(String pCustomerNo) {
        produceCustomerStatus(pCustomerNo, CustomerStatusEnum.UNFROZEN);
    }

    public void producePaymentStatusReceived(Payment pPayment) {

        LOGGER.info(String.format("Submitting Payment Received event: [%s]",
                pPayment.toString()));

        try (Producer<String, PaymentStatus> paymentStatusEventProducer =
                     new KafkaProducer<>(getProducerConfig())) {

            paymentStatusEventProducer.send(new ProducerRecord<>(kafkaConfig.getTopicPaymentStatus(),
                    PaymentStatus.newBuilder().setPaymentId(pPayment.getPaymentId())
                            .setOrderId(pPayment.getOrderId())
                            .setStatus(PaymentStatusEnum.PAYMENT_RECEIVED).build()));
        }

    }

    private void produceCustomerStatus(String pCustomerNo, CustomerStatusEnum pCustomerStatus) {

        LOGGER.info(String.format("Submitting Customer Status event: [%s, %s]", pCustomerNo,
                pCustomerStatus));
        try (Producer<String, CustomerStatus> customerStatusEventProducer =
                     new KafkaProducer<>(getProducerConfig())) {

            customerStatusEventProducer
                    .send(new ProducerRecord<>(kafkaConfig.getTopicCustomerStatus(),
                            CustomerStatus.newBuilder().setCustomerNo(pCustomerNo)
                                    .setStatus(pCustomerStatus).build()));

        }
    }
}
