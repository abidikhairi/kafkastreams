package com.majesteye.tutorials.twitter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.v1.StallWarning;
import twitter4j.v1.Status;
import twitter4j.v1.StatusDeletionNotice;
import twitter4j.v1.StatusListener;

import java.util.Properties;

public class StreamListener implements StatusListener {

    private final KafkaProducer<Long, String> producer;

    private final String topic;


    public StreamListener(String topic, Properties kafkaConfig) {
        super();

        this.topic = topic;
        this.producer = new KafkaProducer<>(kafkaConfig);
    }

    @Override
    public void onStatus(Status status) {
        this.producer.send(new ProducerRecord<>(this.topic, status.getId(), status.getText()));
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }
}
