package com.example.newsapplication.data

import android.os.Parcelable
import java.io.Serializable


data class NewsResponse(
    val status : String?=null,
    val totalResults : Int=0,
    val articles : List<Articles>?=null

):Serializable

data class Articles (

    val source : Source?=null,
    val author : String?=null,
    val title : String?=null,
    val description : String?=null,
    val url : String?=null,
    val urlToImage : String?=null,
    val publishedAt : String?=null,
    val content : String?=null
):Serializable


data class Source (

    val id : String?=null,
    val name : String?=null
):Serializable







