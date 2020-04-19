package com.reyes.tutorial.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 1. The base class AbstractElasticsearchConfiguration already provides the elasticsearchTemplate bean
 * 2. EnableElasticsearchRepositories，he EnableElasticsearchRepositories annotation activates the Repository support. If no base package is configured, it will use the one of the configuration class it is put on。
 * 		scan the configure packages
 * 		
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.reyes.tutorial.repositories")
public class ESRestHighLevelClientConfig extends AbstractElasticsearchConfiguration {
	
	@Override
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//				.connectedTo("localhost:9200", "localhost:9201").build();
				.connectedTo("localhost:9200").build();

		return RestClients.create(clientConfiguration).rest();
	}
	
//	Using the Meta Model Object Mapping ElasticsearchMapper.(指定mapping方式)
//	Overwrite the default EntityMapper from ElasticsearchConfigurationSupport and expose it as bean.
	@Bean
	@Override
	public EntityMapper entityMapper() {
		
//		Use the provided SimpleElasticsearchMappingContext to avoid inconsistencies and provide a GenericConversionService for Converter registration.
		ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
				new DefaultConversionService());
		
//		Optionally set CustomConversions if applicable.
		entityMapper.setConversions(elasticsearchCustomConversions());

		return entityMapper;
	}
}
