package com.soaringclouds.webshop.financialsmicroservice;

import com.soaringclouds.avro.customerstatus.v1.CustomerStatus;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by svb on 04.03.18.
 */
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}") private String bootstrapServer;

    @Value("${spring.kafka.schema-registry-url}") private String schemaRegistryUrl;

    @Bean
    public Map<String, Object> producerConfiguration() {

	final Map<String, Object> properties = new HashMap<>();
	properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
	properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
	properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

	return properties;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {

        final Map<String, Object> properties = new HashMap<>();
	properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
	properties.put(ConsumerConfig.GROUP_ID_CONFIG, "financials");
	properties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
	properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
	properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

	return properties;
    }

    @Bean
    public Map<String, Object> consumerConfigsNonAvro() {

	final Map<String, Object> properties = new HashMap<>();
	properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	properties.put(ConsumerConfig.GROUP_ID_CONFIG, "financials");

	return properties;
    }

    @Bean
    public ProducerFactory<String, PaymentStatus> producerFactoryPaymentStatus() {
	return new DefaultKafkaProducerFactory<>(producerConfiguration());
    }

    @Bean
    public ProducerFactory<String, CustomerStatus> producerFactoryCustomerStatus() {
	return new DefaultKafkaProducerFactory<>(producerConfiguration());
    }

    @Bean
    public KafkaTemplate<String, PaymentStatus> paymentStatusProducer() {

	return new KafkaTemplate<>(producerFactoryPaymentStatus());
    }

    @Bean
    public KafkaTemplate<String, CustomerStatus> customerStatusProducer() {

	return new KafkaTemplate<>(producerFactoryCustomerStatus());
    }

    @Bean
    public ConsumerFactory<String, PaymentStatus> consumerFactory() {
	return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactoryNonAvro() {
	return new DefaultKafkaConsumerFactory<>(consumerConfigsNonAvro());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentStatus> kafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, PaymentStatus> factory = new
			ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory());

	return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryNonAvro() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new
			ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactoryNonAvro());
	factory.setRecordFilterStrategy(
			record -> record.value().contains("\"shippingStatus\": \"new\""));
	return factory;
    }
}
