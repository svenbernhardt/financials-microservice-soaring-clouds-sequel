package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.webshop.financialsmicroservice.config.KafkaConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.util.Optional;

public class KafkaConusmerEndpointBuilder {

    private static final String DEFAULT_GROUP_ID = "financials";

    private String topicName;
    private String groupId;
    private String bootstrapServers;
    private Optional<String> schemaRegistryUrl;

    private Class<?> keyDeserializerClass;
    private Class<?> valueDeserializerClass;


    public KafkaConusmerEndpointBuilder(KafkaConfig pKafkaConfig) {

        groupId = DEFAULT_GROUP_ID;
        bootstrapServers = pKafkaConfig.getBootstrapServer();
        schemaRegistryUrl = Optional.ofNullable(pKafkaConfig.getSchemaRegistryUrl());
    }

    public static KafkaConusmerEndpointBuilder createAvroConsumerForTopic(KafkaConfig pKafkaConfig, String pTopicName) {

        return new KafkaConusmerEndpointBuilder(pKafkaConfig).forTopic(pTopicName).withKeyDeserializer(KafkaAvroDeserializer.class)
                .withValueDeserializer(KafkaAvroDeserializer.class);
    }

    public KafkaConusmerEndpointBuilder forTopic(String pTopicName) {

        topicName = pTopicName;
        return this;
    }

    public KafkaConusmerEndpointBuilder withGroupId(String pGroupId) {

        groupId = pGroupId;
        return this;
    }

    public KafkaConusmerEndpointBuilder withKeyDeserializer(Class<?> pClass) {

        keyDeserializerClass = pClass;
        return this;
    }


    public KafkaConusmerEndpointBuilder withValueDeserializer(Class<?> pClass) {

        valueDeserializerClass = pClass;
        return this;
    }

    public KafkaConusmerEndpointBuilder withSchemaRegistryUrl(String pSchemaRegistryUrl) {

        schemaRegistryUrl = Optional.of(pSchemaRegistryUrl);
        return this;
    }

    public String build() {

        String endpointConfigurationString;

        endpointConfigurationString = String.format("kafka:%s?brokers=%s&maxPollRecords=1" +
                        "&consumersCount=1&groupId=%s" +
                        "&serializerClass=kafka.serializer" +
                        ".StringEncoder&keyDeserializer=%s&valueDeserializer=%s", topicName,
                bootstrapServers, groupId, keyDeserializerClass.getName(),
                valueDeserializerClass.getName());

        if (schemaRegistryUrl.isPresent()) {
            endpointConfigurationString += String.format("&schemaRegistryURL=%s",
                    schemaRegistryUrl.get());
        }

        return endpointConfigurationString;
    }
}

