package com.example.newsapplication.api

data class NewsApiJSON(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)