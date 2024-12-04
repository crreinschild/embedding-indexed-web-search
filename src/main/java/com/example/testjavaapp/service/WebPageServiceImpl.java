package com.example.testjavaapp.service;

import com.example.testjavaapp.model.*;
import org.jsoup.Jsoup;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebPageServiceImpl implements WebPageService {
    private final EmbeddingModel embeddingModel;
    private final ElasticSearchService elasticSearchService;

    public WebPageServiceImpl(@Autowired EmbeddingModel embeddingModel, @Autowired ElasticSearchService elasticSearchService) {
        this.embeddingModel = embeddingModel;
        this.elasticSearchService = elasticSearchService;
    }

    @Override
    public void addUrl(String url) throws IOException, InterruptedException {
        // if robots allowed, read URL content
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

        WebPage webPage = new WebPage();
        webPage.setUrl(url);
        webPage.setTitle(doc.title());

        String text = doc.text();

        // take content and generate embedding of it
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(text));
        float[] embedding = embeddingResponse.getResult().getOutput();
        webPage.setContentVector(embedding);

        // index the embedding into elastic search
        elasticSearchService.postElastic(webPage);
    }

    @Override
    public List<WebPage> search(String query) throws IOException, InterruptedException {
        // basic validation
        if (query == null || query.trim().isEmpty())
            return null;

        // generate embedding
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(query));
        float[] embedding = embeddingResponse.getResult().getOutput(); //getEmbedding(query);

        // search elasticsearch by knn
        SearchResponse response = elasticSearchService.searchElastic(embedding);
        List<WebPage> webPages = new ArrayList<>();
        for (SearchResponseHit hit : response.hits.hits) {
            WebPage webPage = new WebPage();
            webPage.setUrl(hit.fields.url[0]);
            webPage.setTitle(hit.fields.title[0]);
            webPages.add(webPage);
        }
        return webPages;
    }
}
