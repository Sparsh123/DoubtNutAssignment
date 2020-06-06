package com.example.newsapplication.ui

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import com.example.newsapplication.data.Articles
import com.example.newsapplication.data.NewsResponse
import com.example.newsapplication.ui.ApiClient.getClient
import com.example.newsapplication.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.util.concurrent.TimeUnit


class NewsViewModel :
        BaseViewModel<NewsNavigator>() {
    var newsResp:NewsResponse?=null

    fun getNews(context:Context):NewsResponse
    {
        val apiService =
            ApiClient.getClient(context =context)?.create(ApiService::class.java)

        // Fetching all news
        apiService?.fetchAllNews()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                override fun onSuccess(newsResponse: NewsResponse) { // Receive all news
                newsResp = newsResponse

                }

                override fun onError(e: Throwable) { // Network error
                    Toast.makeText(context, "Error while fetching news!", Toast.LENGTH_LONG).show()
                }
            })

        return newsResp!!
    }



}

object ApiClient {
    var base_url:String = "https://newsapi.org/"
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null
    fun getClient(context: Context): Retrofit? {
        if (okHttpClient == null) initOkHttp(context)
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun initOkHttp(context: Context) {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
           override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")


                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        okHttpClient = httpClient.build()
    }
}

interface ApiService {

    // Fetch all news
    @GET("docs/endpoints/top-headlines)")
    fun fetchAllNews(): Single<NewsResponse>?


}