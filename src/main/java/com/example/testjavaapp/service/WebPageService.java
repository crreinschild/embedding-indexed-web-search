package com.example.testjavaapp.service;

import com.example.testjavaapp.model.WebPage;

import java.io.IOException;
import java.util.List;

public interface WebPageService {
    void addUrl(String url) throws IOException, InterruptedException;
    List<WebPage> search(String query) throws IOException, InterruptedException;
}
