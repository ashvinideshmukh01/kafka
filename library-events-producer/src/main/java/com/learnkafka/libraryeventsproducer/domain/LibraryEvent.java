package com.learnkafka.libraryeventsproducer.domain;

public class LibraryEvent {
	
	private Integer libraryEventId;
	private Book book;
	private LobraryEventType lobraryEventType;

	public LibraryEvent(Integer libraryEventId, Book book) {
		super();
		this.libraryEventId = libraryEventId;
		this.book = book;
	}

	public LibraryEvent() {
		super();
	}

	public Integer getLibraryEventId() {
		return libraryEventId;
	}

	public void setLibraryEventId(Integer libraryEventId) {
		this.libraryEventId = libraryEventId;
	}

	
	public LobraryEventType getLobraryEventType() {
		return lobraryEventType;
	}

	public void setLobraryEventType(LobraryEventType lobraryEventType) {
		this.lobraryEventType = lobraryEventType;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	
}
