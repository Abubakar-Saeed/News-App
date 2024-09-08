package com.practice.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsArticleResponses(

    val status : String,
    val totalResults : Int,
    val articles : List<NewsResponse>

)

@Parcelize
data class NewsResponse(

    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val newsUrl: String?,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val date: String?,
    @SerializedName("content") val content: String?

) : Parcelable
