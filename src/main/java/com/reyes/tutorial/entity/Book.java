package com.reyes.tutorial.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "springboot08", type = "book")
public class Book {
	
//	Type Hints，By default the domain types class name is used for the type hint.
//	在es中會顯示Hints為，_class com.reyes.tutorial.entity.Book
	
//	@Id會僵直對應到 es中的 _id
	@Id
	private String id;
	private String bookName;
	private String authName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", authName=" + authName + "]";
	}
	
}
