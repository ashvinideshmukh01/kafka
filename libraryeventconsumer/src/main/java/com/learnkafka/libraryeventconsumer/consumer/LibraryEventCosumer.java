package com.learnkafka.libraryeventconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LibraryEventCosumer {
	
	@KafkaListener(topics = "library-events")
	public void onMessage(ConsumerRecord<Integer, String> consumerRecords) {
		log.info("consumer records "+consumerRecords);
	}
}
