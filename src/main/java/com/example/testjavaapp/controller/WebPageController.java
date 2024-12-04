package com.example.testjavaapp.controller;

import com.example.testjavaapp.model.WebPage;
import com.example.testjavaapp.service.WebPageServiceImpl;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.ElasticsearchVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class WebPageController {
    @Autowired
    WebPageServiceImpl webPageService;

    @PostMapping("/")
    public void addUrl(@RequestBody UrlRequest request) throws IOException, InterruptedException {
        webPageService.addUrl(request.url);
    }

    @GetMapping("/search")
    public List<WebPage> search(@RequestParam String query) throws IOException, InterruptedException {
        return webPageService.search(query);
    }

    public static class UrlRequest {
        public String url;
    }
}
