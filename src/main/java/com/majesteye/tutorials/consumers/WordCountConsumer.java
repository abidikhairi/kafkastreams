package com.majesteye.tutorials.consumers;

import com.majesteye.tutorials.WordCountKStreams;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class WordCountConsumer {


    public static void main(String[] args) {
        Properties kafkaConfig = new Properties();

        kafkaConfig.put("bootstrap.servers", "localhost:29092");
        kafkaConfig.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConfig.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        kafkaConfig.put("group.id", "group-1");

        KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(kafkaConfig);
        consumer.subscribe(Collections.singletonList(WordCountKStreams.OUT_TOPIC));

        while (true) {
            for (ConsumerRecord<String, Long> record : consumer.poll(Duration.ofSeconds(3))) {
                String key = record.key();
                Long value = record.value();

                System.out.println(String.format("Word: %s\tCount: %d", key, value));
            }
        }
    }
}
