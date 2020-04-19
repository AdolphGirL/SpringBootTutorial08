package com.reyes.tutorial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reyes.tutorial.entity.Book;
import com.reyes.tutorial.repositories.BookRepository;

@RestController
public class TestController {
	
	private ElasticsearchOperations elasticsearchOperations;
	
//	同autowired
	public TestController(ElasticsearchOperations elasticsearchOperations) { 
		this.elasticsearchOperations = elasticsearchOperations;
	}
	
//	暫不寫Service，只接使用Dao
	@Autowired
	private BookRepository bookRepository;
	
//	Store some entity in the Elasticsearch cluster.
	@PostMapping("/book")
	public String save(@RequestBody Book book) {
		IndexQuery indexQuery = new IndexQueryBuilder()
					.withId(book.getId().toString()).withObject(book).build();
		String documentId = elasticsearchOperations.index(indexQuery);
		return documentId;
	}
	
	@GetMapping("/book/{bookName}")
	public List<Book> findById(@PathVariable("bookName") String bookName) {
//		Person person = elasticsearchOperations.queryForObject(GetQuery.getById(id.toString()), Person.class);
//		return person;
		
		return bookRepository.findByBookName(bookName);
	}
	
}
