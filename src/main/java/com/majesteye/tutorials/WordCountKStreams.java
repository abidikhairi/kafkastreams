package com.majesteye.tutorials;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author flursky
 *
 */
public class WordCountKStreams {

    public static final String IN_TOPIC = "TUTORIAL_TEXT";
    public static final String OUT_TOPIC = "TUTORIAL_WORDCOUNT";


    public static void main( String[] args )  {
        Properties kafkaConfig = new Properties();

        kafkaConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-streams");
        kafkaConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        kafkaConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(IN_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        source.flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .map((key, value) -> new KeyValue<>(value, 1L))
                .groupByKey()
                .reduce(Long::sum)
                .toStream()
                .filter((key, value) -> value > 50)
                .to(OUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, kafkaConfig);

        System.out.println(topology.describe());

        streams.start();
    }
}
