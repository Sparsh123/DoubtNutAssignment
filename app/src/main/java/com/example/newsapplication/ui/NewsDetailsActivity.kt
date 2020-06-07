package com.tracki.ui.rides

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapplication.R
import com.example.newsapplication.data.Articles
import com.example.newsapplication.data.NewsResponse


class NewsDetailsActivity :AppCompatActivity() {

    private var newsResponse:NewsResponse?=null
    private var webView:WebView?=null
    private var mList:List<Articles>?=null
    private var pos:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        webView=findViewById(R.id.webView)
        webView!!.setWebViewClient(object:WebViewClient() {
            override fun onPageFinished(view:WebView, url:String) {
                webView!!.scrollTo(100,100);
                webView!!.setInitialScale(100);
            }})
        webView!!.requestFocusFromTouch();
        webView!!.setVerticalScrollBarEnabled(true);
        webView!!.setHorizontalScrollBarEnabled(true);
        val bundle = intent.extras
        newsResponse = bundle!!.getSerializable("NewsResponse") as NewsResponse
        mList=newsResponse!!.articles
        pos=bundle.getString("Pos")
        setup()
    }

    fun setup()
    {
        if(mList!![pos!!.toInt()].urlToImage!=null) {
            val uri: String = mList!![pos!!.toInt()].url!!
            webView!!.getSettings().setJavaScriptEnabled(true);
            webView!!.getSettings().setUseWideViewPort(true);
            webView!!.getSettings().setLoadWithOverviewMode(true);
            webView!!.getSettings().setBuiltInZoomControls(true);
            webView!!.loadUrl(uri)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, NewsDetailsActivity::class.java)
    }
}
