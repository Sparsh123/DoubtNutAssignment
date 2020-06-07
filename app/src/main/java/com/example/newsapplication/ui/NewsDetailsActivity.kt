package com.tracki.ui.rides

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapplication.R
import com.example.newsapplication.data.Articles
import com.example.newsapplication.data.NewsResponse


class NewsDetailsActivity :AppCompatActivity() {

    private var newsResponse:NewsResponse?=null
    private var ivNewsDetails:ImageView?=null
    private var tvSource:TextView?=null
    private var tvAuthor:TextView?=null
    private var tvTitle:TextView?=null
    private var tvDetail:TextView?=null
    private var tvPub:TextView?=null
    private var webView:WebView?=null
    private var mList:List<Articles>?=null
    private var pos:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
//        ivNewsDetails = findViewById(R.id.ivNewsDetails)
//        tvSource=findViewById(R.id.tvSource)
//        tvAuthor= findViewById(R.id.tvAuthor)
//        tvTitle = findViewById(R.id.tvTitle)
//        tvDetail=findViewById(R.id.tvDetail)
//        tvPub=findViewById(R.id.tvPub)
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

//        tvSource!!.setText("Source: " + mList!![pos!!.toInt()].source)
//        tvAuthor!!.setText("Author: " + mList!![pos!!.toInt()].author)
//        tvTitle!!.setText(mList!![pos!!.toInt()].title.toString())
//        tvDetail!!.setText(mList!![pos!!.toInt()].content.toString())
//        var words:List<String>?=null
//        words = mList!![pos!!.toInt()].publishedAt!!.split("T")
//        tvPub!!.setText("Published at: " + words.get(0) + ", " + words.get(1) )



    }


    companion object {
        fun newIntent(context: Context) = Intent(context, NewsDetailsActivity::class.java)
    }


}

//class WebViewController : WebViewClient() {
//    override fun shouldOverrideUrlLoading(
//        view: WebView,
//        url: String
//    ): Boolean {
//        view.loadUrl(url)
//        return true
//    }
//
//    override fun onPageFinished(view: WebView?, url: String?) {
//        webView.scrollTo(100, 100)
//        webView.setInitialScale(100)
//    }
//}