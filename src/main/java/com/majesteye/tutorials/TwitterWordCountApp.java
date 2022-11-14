package com.majesteye.tutorials;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;

import java.util.Arrays;
import java.util.Properties;

public class TwitterWordCountApp {

    public static String IN_TOPIC = "twitter_tweets";

    public static String OUT_TOPIC = "twitter_wordcount";

    public static void main(String[] args) {
        Properties kafkaConfig = new Properties();

        kafkaConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "twitter-wordcount-streams");
        kafkaConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

        kafkaConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());

        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(IN_TOPIC, Consumed.with(Serdes.Long(), Serdes.String()))
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .map((key, value) -> KeyValue.pair(value, 1L))
                .groupByKey()
                .reduce((a, b) -> a + b)
                .toStream()
                .filter((key, value) -> value > 50)
                .to(OUT_TOPIC);
    }
}
