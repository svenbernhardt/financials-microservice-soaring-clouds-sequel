package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class KafkaConsumerEndpointBuilderTest {

    public static final Logger LOGGER =
            Logger.getLogger(KafkaConsumerEndpointBuilderTest.class.getSimpleName());

    public static final String TEST_BOOTSTRAP_SERVER = "kafka:9092";

    public static String EXPECTED_KAFKA_ENDPOINT_STRING = "kafka:soaring-shippingnews?brokers" +
            "=kafka:9092"
            + "&maxPollRecords=1"
            + "&consumersCount=1"
            + "&groupId=financials"
            + "&serializerClass=kafka.serializer.StringEncoder"
            + "&keyDeserializer=org.apache.kafka.common.serialization.StringDeserializer"
            + "&valueDeserializer=org.apache.kafka.common.serialization.StringDeserializer";

    public static String EXPECTED_KAFKA_ENDPOINT_STRING_WITH_REGISTRY = "kafka:soaring" +
            "-shippingnews?brokers" +
            "=kafka:9092"
            + "&maxPollRecords=1"
            + "&consumersCount=1"
            + "&groupId=financials"
            + "&serializerClass=kafka.serializer.StringEncoder"
            + "&keyDeserializer=org.apache.kafka.common.serialization.StringDeserializer"
            + "&valueDeserializer=io.confluent.kafka.serializers.KafkaAvroDeserializer"
            + "&schemaRegistryURL=kafka-registry:9000";

    private KafkaConfig kafkaConfig;

    @BeforeEach
    public void setUp() {

        kafkaConfig = new KafkaConfig(TEST_BOOTSTRAP_SERVER, null);
    }

    @Test
    public void whenConfigSetWithoutSchemaRegistryThenReturnKafkaConsumerEndpointStringWithoutSchemaRegistry() {

        KafkaConusmerEndpointBuilder kafkaConusmerEndpointBuilder =
                new KafkaConusmerEndpointBuilder(kafkaConfig).forTopic("soaring-shippingnews").withKeyDeserializer(StringDeserializer.class)
                        .withValueDeserializer(StringDeserializer.class);

        assertThat(kafkaConusmerEndpointBuilder.build(), equalTo(EXPECTED_KAFKA_ENDPOINT_STRING));
    }

    @Test
    public void whenAllConfigSetThenReturnKafkaConsumerEndpointStringIncludingSchemaRegistry() {

        KafkaConusmerEndpointBuilder kafkaConusmerEndpointBuilder =
                new KafkaConusmerEndpointBuilder(kafkaConfig).forTopic("soaring-shippingnews").withKeyDeserializer(StringDeserializer.class)
                        .withValueDeserializer(KafkaAvroDeserializer.class).withSchemaRegistryUrl("kafka-registry:9000");

        assertThat(kafkaConusmerEndpointBuilder.build(),
                equalTo(EXPECTED_KAFKA_ENDPOINT_STRING_WITH_REGISTRY));
    }
}
