package com.soaringclouds.webshop.financialsmicroservice;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatusEnum;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import static com.soaringclouds.webshop.financialsmicroservice.util.TestUtilities.createOrder;

public class KafkaProducerTest {

    private final static String BOOTSTRAP_SERVERS_URL = "130.61.35.61:9092";
    private final static String SCHEMA_REGISTRY_URL = "http://130.61.35.61:8081";

    private static final Logger LOGGER = Logger.getLogger(KafkaProducerTest.class.getName());

    public static void main(String[] pArgs) throws Exception {
        //createShippingNewsMessage();
        createPaymentMessage();
        //createOrderCreatedMessage();
    }

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS_URL);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    private static <T> Producer<String, T> createAvroProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS_URL);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                KafkaAvroSerializer.class);
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                SCHEMA_REGISTRY_URL);
        return new KafkaProducer(props);
    }

    static void createShippingNewsMessage() throws Exception {

        final byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/shippingnews_event" +
                ".json"));

        runProducer("soaring-shippingnews", new String(bytes,
                StandardCharsets.UTF_8));
    }

    static void createPaymentMessage() throws Exception {

        final PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setOrderId("order0801");
        paymentStatus.setPaymentId("payment0801");
        paymentStatus.setStatus(PaymentStatusEnum.PAYMENT_RECEIVED);

        runAvroProducer("soaring-paymentstatus", paymentStatus, PaymentStatus.class);
    }

    static void createOrderCreatedMessage() throws Exception {

        Order order = createOrder();

        runAvroProducer("soaring-ordercreated", order, Order.class);
    }

    static <T> void runAvroProducer(String pTargetTopic, T avroObject, Class<T> pAvroObjectClass) throws Exception {

        Producer<String, T> producer = createAvroProducer();

        long time = System.currentTimeMillis();

        try {
            final ProducerRecord<String, T> record =
                    new ProducerRecord<>(pTargetTopic, avroObject);

            RecordMetadata metadata = producer.send(record).get();

            long elapsedTime = System.currentTimeMillis() - time;
            LOGGER.info(String.format("sent record(key=%s value=%s) " +
                            "meta(partition=%d, offset=%d) time=%d\n",
                    record.key(), record.value(), metadata.partition(),
                    metadata.offset(), elapsedTime));

        } finally {
            producer.flush();
            producer.close();
        }
    }

    static void runProducer(String pTargetTopic, String pMessage) throws Exception {

        Producer<Long, String> producer = createProducer();
        ;

        long time = System.currentTimeMillis();

        try {
            final ProducerRecord<Long, String> record =
                    new ProducerRecord<>(pTargetTopic, pMessage);

            RecordMetadata metadata = producer.send(record).get();

            long elapsedTime = System.currentTimeMillis() - time;
            LOGGER.info(String.format("sent record(key=%s value=%s) " +
                            "meta(partition=%d, offset=%d) time=%d\n",
                    record.key(), record.value(), metadata.partition(),
                    metadata.offset(), elapsedTime));

        } finally {
            producer.flush();
            producer.close();
        }
    }
}
