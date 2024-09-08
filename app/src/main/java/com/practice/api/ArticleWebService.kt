package com.practice.api

import com.practice.responses.NewsArticleResponses
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ArticleWebService {


    private lateinit var api : ArticleApi

    init {

        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).
            build()

        api = retrofit.create(ArticleApi::class.java)
    }

   suspend fun getArticles(sources : String,pageSize: Int,apiKey: String) : NewsArticleResponses{


       return api.getAllNews(sources,pageSize,apiKey)

    }
   suspend fun getCategoryArticle(country: String,category: String, pageSize: Int,apiKey: String) : NewsArticleResponses{

        return api.getCategoryNews(country, category, pageSize, apiKey)
    }
}

interface ArticleApi{


    @GET("top-headlines")
   suspend fun getAllNews(

        @Query("sources") sources: String,
        @Query("pageSize") pageSize : Int,
        @Query("apikey") apiKey :String


    ) : NewsArticleResponses
    @GET("top-headlines")
    suspend fun getCategoryNews(

        @Query("country") country : String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apikey") apiKey: String


    ) : NewsArticleResponses
}