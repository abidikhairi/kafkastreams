## Introduction to Stream Processing with Apache Kafka

This repository contains the code for the [Stream Processing with Apache Kafka](https://abidikhairi.github.io/p/introduction-to-stream-processing-with-apache-kafka/) Blog.

## Files:
- `docker-compose.yml` - Docker compose file to start Zookeeper and Kafka Broker

## Classes:
- `com.majesteye.tutorials.consumers.WordCountConsumer`: Consumer that counts the number of words in a stream of tweets
- `com.majesteye.tutorials.producers.FakeTextProducer`: Producer that generates fake text
- `com.majesteye.tutorials.WordCountKStreams`: Main class to run word count with Kafka Streams and write results to be consumed by `WordCountConsumer`
- `com.majesteye.tutorials.producers.TwitterProducer`: Producer that reads tweets from Twitter
- `com.majesteye.tutorials.consumers.TwitterWordCountConsumer`: Consumer that prints tweets to the console
- `com.majesteye.tutorials.twitter.StreamListener`: Twitter Stream Listener that reads tweets from Twitter and writes them to Kafka
- `com.majesteye.tutorials.TwitterWordCountApp`: Main class to run word count with Twitter and Kafka Streams and write results to be consumed by `TwitterWordCountConsumer`

## How to run:
- Start Zookeeper and Kafka Broker with `docker-compose up`
- Run `TweetProducer` to start reading tweets from Twitter and writing them to Kafka
- Run `TwitterWordCountApp` to start reading tweets from Twitter and writing them to Kafka
- Run `TwitterWordCountConsumer` to start reading tweets from Kafka and printing them to the console
