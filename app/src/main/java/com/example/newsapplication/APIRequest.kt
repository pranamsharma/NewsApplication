package com.example.newsapplication


import com.example.newsapplication.api.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v2/top-headlines?country=us&category=business&apiKey=8abc91953059403d8650f286bad88263")
    suspend fun getNews(): NewsApiJSON
}