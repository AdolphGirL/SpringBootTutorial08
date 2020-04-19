package com.reyes.tutorial.repositories;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.reyes.tutorial.entity.Book;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
	
	public List<Book> findByBookName(String bookName);
	
}
