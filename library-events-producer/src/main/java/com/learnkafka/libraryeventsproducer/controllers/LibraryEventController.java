package com.learnkafka.libraryeventsproducer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.libraryeventsproducer.domain.LibraryEvent;
import com.learnkafka.libraryeventsproducer.domain.LobraryEventType;
import com.learnkafka.libraryeventsproducer.producer.LibraryEventProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LibraryEventController {
	@Autowired
	LibraryEventProducer libraryEventProducer;
	
	@PostMapping("/v/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		log.info("befoe send library event");
		libraryEvent.setLobraryEventType(LobraryEventType.NEW);
		libraryEventProducer.sendLibraryEvent_approach2(libraryEvent);
		log.info("after send library event");
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
		
	}
	
	@PutMapping("/v/libraryevent")
	public ResponseEntity<?> putLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		log.info("befoe send library event");
		if(libraryEvent.getLibraryEventId()==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KIndly provide libraryEventid!!");
		}
		libraryEvent.setLobraryEventType(LobraryEventType.UPDATE);
		libraryEventProducer.sendLibraryEvent_approach2(libraryEvent);
		log.info("after send library event");
		return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
		
	}
}
