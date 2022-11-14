package com.majesteye.tutorials.producers;

import com.majesteye.tutorials.TwitterWordCountApp;
import com.majesteye.tutorials.twitter.StreamListener;
import twitter4j.Twitter;
import twitter4j.v1.*;

import java.util.Properties;

public class TweetProducer {

    public static void main(String[] args) {
        String consumerKey = System.getenv("CONSUMER_KEY");
        String consumerSecret = System.getenv("CONSUMER_SECRET");
        String accessToken = System.getenv("ACCESS_TOKEN");
        String accessTokenSecret = System.getenv("ACCESS_TOKEN_SECRET");

        Properties kafkaConfig = new Properties();

        kafkaConfig.put("bootstrap.servers", "localhost:29092");
        kafkaConfig.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        kafkaConfig.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Twitter.TwitterBuilder twitterBuilder = Twitter.newBuilder()
                .prettyDebugEnabled(true)
                .oAuthConsumer(consumerKey, consumerSecret)
                .oAuthAccessToken(accessToken, accessTokenSecret);

        twitterBuilder.listener(new StreamListener(TwitterWordCountApp.IN_TOPIC, kafkaConfig))
                .build().v1().stream()
                .filter(FilterQuery.ofTrack("COVID-19", "TUNISIA"))
                .sample();

    }
}
