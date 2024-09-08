package com.practice.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.repository.ArticleRepository
import com.practice.responses.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class NewsViewModel(private val repository: ArticleRepository = ArticleRepository()) : ViewModel() {

    private val _allNewsList = MutableStateFlow<List<NewsResponse>>(emptyList())
    val allNews: StateFlow<List<NewsResponse>> = _allNewsList.asStateFlow()

    private val _allCategoryNewsList = MutableStateFlow<List<NewsResponse>>(emptyList())
    val allCategoryNews: StateFlow<List<NewsResponse>> = _allCategoryNewsList.asStateFlow()


    private val _selectedNews = MutableStateFlow<NewsResponse?>(null)
    val selectedNews: StateFlow<NewsResponse?> = _selectedNews.asStateFlow()


    fun getAllNews(sources: String, pageSize: Int, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = repository.getArticles(sources, pageSize, apiKey)
            _allNewsList.value = newsResponse.articles
        }
    }

    fun getCategoryAllNews(country: String, category: String, pageSize: Int, apiKey: String) {
        viewModelScope.launch {
            val newsResponse = repository.getCategoryArticles(country, category, pageSize, apiKey)
            _allCategoryNewsList.value = newsResponse.articles
        }
    }

    fun updateSelectedNews(news: NewsResponse) {

        _selectedNews.value = news

    }


}

