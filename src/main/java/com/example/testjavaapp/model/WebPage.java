package com.example.testjavaapp.model;

import java.util.UUID;

public class WebPage {
    private UUID id;
    private String url;
    private String title;
    private float[] contentVector;

    public WebPage() {
        super();
    }

    public WebPage(UUID id, String url) {
        super();
        this.id = id;
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float[] getContentVector() {
        return contentVector;
    }

    public void setContentVector(float[] contentVector) {
        this.contentVector = contentVector;
    }
}
