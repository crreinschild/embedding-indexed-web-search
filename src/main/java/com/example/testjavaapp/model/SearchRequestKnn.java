package com.example.testjavaapp.model;

public class SearchRequestKnn {
    public String field;
    public float[] queryVector;
    public int k = 10;
    public int num_candidates = 100;
}