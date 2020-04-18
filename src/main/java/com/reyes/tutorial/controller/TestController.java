package com.reyes.tutorial.controller;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reyes.tutorial.entity.Book;

@RestController
public class TestController {
	
	private ElasticsearchOperations elasticsearchOperations;
	
//	同autowired
	public TestController(ElasticsearchOperations elasticsearchOperations) { 
		this.elasticsearchOperations = elasticsearchOperations;
	}
	
//	Store some entity in the Elasticsearch cluster.
	@PostMapping("/book")
	public String save(@RequestBody Book book) {
		IndexQuery indexQuery = new IndexQueryBuilder()
					.withId(book.getId().toString()).withObject(book).build();
		String documentId = elasticsearchOperations.index(indexQuery);
		return documentId;
	}
	
	
	
}
