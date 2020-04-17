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
    - ElasticsearchTemplate
    - extends ElasticsearchRepository
- POJO設定  
  需定義ES存取位置，如下範例
  ```
  @Document(indexName = "ems",type = "_doc", shards = 1, replicas = 0)

  shards = 1, replicas = 0，可選
  ```

  