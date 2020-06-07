package com.example.newsapplication.ui

import android.content.Context
import com.example.newsapplication.data.NewsResponse
import com.example.newsapplication.ui.base.BaseViewModel
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

/* ViewModel contains business logic*/

class NewsViewModel :
        BaseViewModel<NewsNavigator>() {
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
    @GET("v2/top-headlines")
    fun fetchAllNews(@Query("country") country:String, @Query("apiKey") apiKey:String): Single<NewsResponse>?


}

object RetrofitClient {
    private var retrofit: Retrofit? = null
    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl!!)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}

object ApiUtils {
    const val BASE_URL = "https://newsapi.org/"
    val newsResponse: NewsResponse
        get() = RetrofitClient.getClient(BASE_URL)!!.create(NewsResponse::class.java)
}