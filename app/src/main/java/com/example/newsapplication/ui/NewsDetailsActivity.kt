package com.tracki.ui.rides

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.newsapplication.R
import com.example.newsapplication.data.NewsResponse
import com.example.newsapplication.ui.base.BaseActivity
import com.google.gson.Gson

import java.util.*
import javax.inject.Inject


class NewsDetailsActivity :AppCompatActivity() {

    var newsResponse:NewsResponse?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        val bundle = intent.extras
        newsResponse = bundle!!.getSerializable("NewsResponse") as NewsResponse

    }


    companion object {
        fun newIntent(context: Context) = Intent(context, NewsDetailsActivity::class.java)
    }


}