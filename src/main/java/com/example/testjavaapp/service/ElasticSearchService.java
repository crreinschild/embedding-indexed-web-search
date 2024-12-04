package com.example.testjavaapp.service;

import com.example.testjavaapp.model.SearchResponse;
import com.example.testjavaapp.model.WebPage;

import java.io.IOException;

public interface ElasticSearchService {
    void postElastic(WebPage webPage) throws IOException, InterruptedException;

    SearchResponse searchElastic(float[] vector) throws IOException, InterruptedException;
}
