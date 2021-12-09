package com.learnkafka.libraryeventsproducer.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.libraryeventsproducer.domain.LibraryEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventProducer {

	@Autowired
	KafkaTemplate<Integer,String> kafkaTemplate;
	@Autowired
	ObjectMapper objectMapper;
	String topic="library-events";
	
	public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		kafkaTemplate.setDefaultTopic("library-events");
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key,value,result);
			}

//			private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
//				System.out.println("msg handle successfuly!!!"+key+":"+value+":"+"partation "+result.getRecordMetadata().partition());
//			}

			@Override
			public void onFailure(Throwable ex) {
				try {
					handleFailure(key,value,ex);
				} catch (Throwable e) {
					log.error(e.getMessage());
				}
			}

//			private void handleFailure(Integer key, String value, Throwable ex) throws Throwable {
//				log.error("error handlimg in msg and exception is "+ex.getMessage());
//				throw ex;
//			}
		});
	}
	
	public void sendLibraryEvent_approach2(LibraryEvent libraryEvent) throws JsonProcessingException {
		Integer key = libraryEvent.getLibraryEventId();
		String value = objectMapper.writeValueAsString(libraryEvent);
		kafkaTemplate.setDefaultTopic("library-events");
		ProducerRecord<Integer, String> producerRecord=buildProducerRecord(topic,key,value);
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key,value,result);
			}

//			private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
//				System.out.println("msg handle successfuly!!!"+key+":"+value+":"+"partation "+result.getRecordMetadata().partition());
//			}

			@Override
			public void onFailure(Throwable ex) {
				try {
					handleFailure(key,value,ex);
				} catch (Throwable e) {
					log.error(e.getMessage());
				}
			}

//			private void handleFailure(Integer key, String value, Throwable ex) throws Throwable {
//				log.error("error handlimg in msg and exception is "+ex.getMessage());
//				throw ex;
//			}
		});
	}

	private ProducerRecord<Integer, String> buildProducerRecord(String topic2, Integer key, String value) {
		return new ProducerRecord<Integer, String>(topic2, null, key, value, null);
	}

	protected void handleFailure(Integer key, String value, Throwable ex) throws Throwable {
		log.error("error handlimg in msg and exception is "+ex.getMessage());
		throw ex;
	}

	protected void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		System.out.println("msg handle successfuly!!!"+key+":"+value+":"+"partation "+result.getRecordMetadata().partition());
	}
	
}
