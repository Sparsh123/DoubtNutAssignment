package com.example.newsapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.data.Articles
import com.example.newsapplication.data.NewsResponse
import com.example.newsapplication.databinding.ActivityNewsBinding
import com.example.newsapplication.ui.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>(),
    NewsNavigator, NewsAdapter.OnItemClickListener {
    var newsResp:NewsResponse?=null

    @Inject
    lateinit var mNewsViewModel: NewsViewModel
    @Inject
    lateinit var mNewsAdapter: NewsAdapter

    var mService:ApiService?=null


    private lateinit var mActivityNewsBinding: ActivityNewsBinding
    private lateinit var toolbar: Toolbar


    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_news
    override fun getViewModel() = mNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNewsAdapter = NewsAdapter()
        mActivityNewsBinding = viewDataBinding
        mNewsViewModel.navigator = this
        getNews(this)

        //set listener
        mNewsAdapter.setOnItemListener(this)
        toolbar = mActivityNewsBinding.toolbar
        setToolbar(toolbar, getString(R.string.my_news))

        //set Adapter
        mActivityNewsBinding.rvNews.adapter = mNewsAdapter


        //Set List


    }

    fun getNews(context:Context)
    {
//        val mDisposable = CompositeDisposable()

//        val apiService =
//            ApiClient.getClient(context =context)?.create(ApiService::class.java)

//        // Fetching all news
//            apiService?.fetchAllNews()!!
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .observeOn(Schedulers.io())
//            .subscribe(object : SingleObserver<NewsResponse> {
//
//                override fun onSubscribe(d: Disposable) {
//                    Log.d(TAG, "onSubscribe")
//                   // disposable = d
//                }
//
//                override fun onSuccess(newsResponse: NewsResponse) { // Received all news
//                    newsResp = newsResponse
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d(TAG, "onError: " + e.message)
//                }
//            })


//        mService!!.fetchAllNews("us", "3952dfc43da040d7a08cd5e8400c5d11")!!.enqueue(object : Callback<NewsResponse> {
//            override fun onResponse(
//                call: Call<NewsResponse>,
//                response: Response<NewsResponse>
//            ) {
//                if (response.isSuccessful()) {
//                    newsResp = response.body()
//                    Log.d("MainActivity", "posts loaded from API")
//                } else {
//                    val statusCode = response.code()
//                    // handle request errors depending on status code
//                }
//            }
//
//            override fun onFailure(
//                call: Call<NewsResponse>,
//                t: Throwable
//            ) {
//                Log.d("NewsActivity", "error loading from API")
//            }
//        })


        val retrofit: Retrofit =  Retrofit.Builder().baseUrl("https://www.newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        val apiService = ApiClient.getClient(context =context)?.create(ApiService::class.java)

//        val apiService:ApiService  = retrofit.create(ApiService::class.java)

        val call:Call<NewsResponse>  = apiService!!.fetchAllNews("us","3952dfc43da040d7a08cd5e8400c5d11")!!;

        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(call:Call<NewsResponse>, response:Response<NewsResponse>) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Not successful!", Toast.LENGTH_LONG).show()
                }
                newsResp = response.body()
                mNewsAdapter.setList(newsResp!!.articles!!)





            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()

            }
    })
    }




    override fun onClickItem(pos:Int) {
        openNewsFragment(pos);
    }

    fun openNewsFragment(pos:Int)
    {

    }



    companion object {
        private const val TAG = "NewsActivity"
        fun newIntent(context: Context) = Intent(context, NewsActivity::class.java)
    }
}
