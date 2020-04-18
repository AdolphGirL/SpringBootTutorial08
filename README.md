#### spring boot with elasticserach
- spring-boot-starter-data-elasticsearch，spring boot透過 -data- (即默認spring data)來操作ES
- 也可以整合jest來操作ES，但spring boot 2.x後似乎不建議
- 要留意spring boot支持elasticsearch版本的問題
- 官方文檔  
  ``https://spring.io/projects/spring-data-elasticsearch``
- github  
  ``https://github.com/spring-projects/spring-data-elasticsearch``
- 版本對應  
  ``https://docs.spring.io/spring-data/elasticsearch/docs/3.2.0.RC3/reference/html/#preface.versions``
- doc文檔  
  ``https://docs.spring.io/spring-data/elasticsearch/docs/3.2.6.RELEASE/reference/html/#reference``

- spring boot 2.x版，配置，由原先的
  ```
  spring:
    data:
      elasticsearch:
        cluster-name: docker-cluster
        cluster-nodes: localhost:9300
  ```
  改為
  ```
  spring:
    elasticsearch:
      rest:
        uris:
        - http://localhost:9200
        username: xxx
        password: zzz
  ```
- 操作方式
    - Transport Client，is deprecated as of Elasticsearch 7 and will be removed in Elasticsearch 8.  
      We strongly recommend to use the High Level REST Client instead of the TransportClient  
      - 設定方式  

        ```
        import org.springframework.beans.factory.annotation.Autowired;

        @Configuration
        static class Config {
          @Bean
          RestHighLevelClient client() {

            ClientConfiguration clientConfiguration = ClientConfiguration.builder() 
              .connectedTo("localhost:9200", "localhost:9201")
              .build();

            return RestClients.create(clientConfiguration).rest();                  
          }
        }

        // ...

        @Autowired
        RestHighLevelClient highLevelClient;

        RestClient lowLevelClient = highLevelClient.lowLevelClient();

        // ...
        IndexRequest request = new IndexRequest("spring-data", "elasticsearch", randomID())
            .source(singletonMap("feature", "high-level-rest-client"))
            .setRefreshPolicy(IMMEDIATE);

        IndexResponse response = highLevelClient.index(request);
        ```
      - Elasticsearch Object Mapping  
        Jackson Object Mapping；Meta Model Object Mapping；spring boot透過spring data操作資料，透過entityMapper映射物件  
      - Mapping Annotation Overview，The ElasticsearchEntityMapper can use metadata to drive the mapping of objects to documents.The following annotations are available:   
        
        @Id: Applied at the field level to mark the field used for identity purpose.  
        @Document: Applied at the class level to indicate this class is a candidate for mapping to the database  
        @Field: Applied at the field level and defines properties of the field  
        ...
  
    - ElasticsearchTemplate  
      The ElasticsearchTemplate is an implementation of the ElasticsearchOperations interface using the Transport Client.  
    - ElasticsearchRestTemplate  
      The ElasticsearchRestTemplate is an implementation of the ElasticsearchOperations interface using the High Level REST Client.  

      ```
      @Configuration
      public class ESRestHighLevelClientConfig extends AbstractElasticsearchConfiguration {
        
        @Override
        public RestHighLevelClient elasticsearchClient() {
          ClientConfiguration clientConfiguration = ClientConfiguration.builder()
              .connectedTo("localhost:9200", "localhost:9201").build();

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
      ```
    - extends ElasticsearchRepository
- POJO設定  
  需定義ES存取位置，如下範例
  ```
  @Document(indexName = "ems",type = "_doc", shards = 1, replicas = 0)

  shards = 1, replicas = 0，可選
  ```

  