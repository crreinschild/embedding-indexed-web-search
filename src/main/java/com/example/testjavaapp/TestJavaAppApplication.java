package com.example.testjavaapp;

import org.elasticsearch.client.RestClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.ElasticsearchVectorStoreOptions;
import org.springframework.ai.vectorstore.SimilarityFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestJavaAppApplication {

    @Bean
    public ElasticsearchVectorStore vectorStore(EmbeddingModel embeddingModel, RestClient restClient) {
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName("test-index");
        options.setDimensions(768);
        options.setSimilarity(SimilarityFunction.l2_norm);
        return new ElasticsearchVectorStore(options, restClient, embeddingModel, false);
    }


    public static void main(String[] args) {
        SpringApplication.run(TestJavaAppApplication.class, args);
    }
}
