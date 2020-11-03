package com.symphony.filmrental.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

  @Value("${mongoUri}")
  private String mongoUri;

  @Value("${mongoDbName}")
  private String mongoDbName;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(mongoUri);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient(), mongoDbName);
  }
}
