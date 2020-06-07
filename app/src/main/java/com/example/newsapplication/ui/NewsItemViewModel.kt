package com.example.newsapplication.ui

import androidx.databinding.ObservableField
import com.example.newsapplication.data.Articles

class NewsItemViewModel(val articles: Articles, val listener: NewsItemListener) {
    val source = ObservableField<String>("")
    val author = ObservableField<String>("")
    val title = ObservableField<String>("")
    val description = ObservableField<String>("")
    val publishedAt = ObservableField<String>("")

    init {
        if (articles.source != null && articles.source.name != null)
            source.set("Source: " + articles.source.name)

        if (articles.author != null)
            author.set("Author: " + articles.author)

        if (articles.title != null)
            title.set(articles.title)

        if (articles.description != null)
            description.set(articles.description)

        if (articles.publishedAt != null) {
            var words:List<String>?=null
            words = articles.publishedAt.split("T")
            publishedAt.set("Published at: " + words.get(0) + ", " + words.get(1).substring(0,
                words.get(1).length-4 ) )
        }
    }

    fun onItemClick() {
        listener.onClickItem()
    }

    interface NewsItemListener {
        fun onClickItem()
    }
}