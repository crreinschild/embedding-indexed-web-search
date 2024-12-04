package com.example.testjavaapp.service;

import com.example.testjavaapp.model.SearchRequest;
import com.example.testjavaapp.model.SearchRequestKnn;
import com.example.testjavaapp.model.SearchResponse;
import com.example.testjavaapp.model.WebPage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private static final String API_KEY_SECURE_ME = "aXg0dVI1TUJWZGNvR09XTXNFcFo6R0lGaUhlU25TTFdoV0JsOGRxLXBVdw==";

    public void postElastic(WebPage webPage) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String requestBodyString = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create()
                .toJson(webPage);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(requestBodyString);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:9200/test-index/_doc"))
                .header("Authorization", "ApiKey " + API_KEY_SECURE_ME)
                .header("Content-Type", "application/json")
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }


    public SearchResponse searchElastic(float[] vector) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        SearchRequest searchRequest = new SearchRequest();
        SearchRequestKnn searchRequestKnn = new SearchRequestKnn();
        searchRequestKnn.field = "content-vector";
        searchRequestKnn.queryVector = vector;
        searchRequest.knn = searchRequestKnn;
        searchRequest.fields = new String[] { "id", "title", "url" };

        String requestBodyString = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
                .toJson(searchRequest);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(requestBodyString);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:9200/test-index/_search"))
                .header("Authorization", "ApiKey " + API_KEY_SECURE_ME)
                .header("Content-Type", "application/json")
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        SearchResponse responseObject = new Gson().fromJson(response.body(), SearchResponse.class);

        return responseObject;
    }

}
