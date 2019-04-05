package com.soaringclouds.webshop.financialsmicroservice.config;

import com.google.common.collect.Lists;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.helidon.config.Config;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class KafkaConfig {

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "app.kafka.bootstrap-servers")
    private String bootstrapServer;

    @Inject
    @ConfigProperty(name = "app.kafka.schema-registry-url")
    private String schemaRegistryUrl;

    private Map<String, Object> producerConfig;

    private Map<String, Object> consumerConfig;

    private KafkaEndpoint kafkaEndpointNonAvro;

    public Map<String, Object> producerConfiguration() {

        if (Objects.isNull(producerConfig)) {
            producerConfig = new HashMap<>();
            producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    KafkaAvroSerializer.class);
            producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    KafkaAvroSerializer.class);
            producerConfig.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                    schemaRegistryUrl);
        }

        return producerConfig;
    }

    public Map<String, Object> consumerConfigs() {

        if (Objects.isNull(consumerConfig)) {
            consumerConfig = new HashMap<>();
            consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class);
            consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    KafkaAvroDeserializer.class);
            consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "financials");
            consumerConfig.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                    schemaRegistryUrl);
            consumerConfig.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                    schemaRegistryUrl);
            consumerConfig.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        }

        return consumerConfig;
    }

    @Produces
    @Named("consumerNonAvro")
    public KafkaEndpoint kafkaEndpointNonAvro() {

        if (Objects.isNull(kafkaEndpointNonAvro)) {
            Map<String, Object> consumerConfigsNonAvro = new HashMap<>();
            consumerConfigsNonAvro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            consumerConfigsNonAvro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class);
            consumerConfigsNonAvro.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class);
            consumerConfigsNonAvro.put(ConsumerConfig.GROUP_ID_CONFIG, "financials");



            final KafkaConfiguration config = new KafkaConfiguration();
            config.setKeyDeserializer(StringDeserializer.class.getName());
            config.setValueDeserializer(StringDeserializer.class.getName());
            config.setGroupId("financials");

            kafkaEndpointNonAvro = new KafkaEndpoint();
            kafkaEndpointNonAvro.setConfiguration(config);
        }

        return kafkaEndpointNonAvro;
    }
}
