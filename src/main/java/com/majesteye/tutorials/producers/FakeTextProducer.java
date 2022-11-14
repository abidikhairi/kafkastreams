package com.majesteye.tutorials.producers;

import com.github.javafaker.Faker;
import com.majesteye.tutorials.WordCountKStreams;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author flursky
 */
public class FakeTextProducer {


    public static void main(String[] args) throws InterruptedException {
        Properties kafkaConfig = new Properties();

        kafkaConfig.put("bootstrap.servers", "localhost:29092");
        kafkaConfig.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaConfig.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Faker faker = new Faker();
        Producer<String, String> producer = new KafkaProducer<>(kafkaConfig);

        while (true) {
            String text = String.join("\n", faker.lorem().sentences(faker.number().numberBetween(1, 5)));
            System.out.println("==================== SENDING TEXT TO TUTORIAL_TEXT ===========================");
            System.out.println(text);
            System.out.println("==============================================================================");
            producer.send(new ProducerRecord<>(WordCountKStreams.IN_TOPIC, text));
            Thread.sleep(2000);
        }
    }
}
