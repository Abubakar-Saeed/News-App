package com.practice.repository

import com.practice.api.ArticleWebService
import com.practice.responses.NewsArticleResponses
import com.practice.responses.NewsResponse

class ArticleRepository (private val webService: ArticleWebService = ArticleWebService()){


   suspend fun getArticles(sources : String, pageSize: Int,apiKey: String): NewsArticleResponses {

        return webService.getArticles(sources, pageSize, apiKey)

    }

    suspend fun getCategoryArticles(country : String, category : String, pageSize: Int,apiKey: String): NewsArticleResponses {

        return webService.getCategoryArticle(country, category, pageSize, apiKey)

    }

}
