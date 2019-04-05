package com.soaringclouds.webshop.financialsmicroservice;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerTest {

    public static void main(String[] pArgs) {
        consume();
    }

    private static Map<String, Object> consumerConfigsNonAvro() {

        Map<String, Object> consumerConfigsNonAvro = new HashMap<>();
        consumerConfigsNonAvro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "130.61.35.61:9092");
        consumerConfigsNonAvro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        consumerConfigsNonAvro.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        consumerConfigsNonAvro.put(ConsumerConfig.GROUP_ID_CONFIG, "financials");

        return consumerConfigsNonAvro;
    }

    public static void consume() {

        final Map<String, Object> configMap = consumerConfigsNonAvro();

        KafkaConsumer<Long, String> consumerNonAvro = new KafkaConsumer<>(configMap);
        consumerNonAvro.subscribe(Lists.newArrayList("soaring-shippingnews"));

        int noMessageFound = 0;
        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumerNonAvro.poll(Duration.of(1,
                    ChronoUnit.SECONDS));
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > 1)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }
            //print each record.
            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
            });
            // commits the offset of record to broker.
            consumerNonAvro.commitAsync();
        }
        consumerNonAvro.close();
    }
}
