package com.example.newsapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.data.NewsResponse
import com.example.newsapplication.databinding.ActivityNewsBinding
import com.example.newsapplication.ui.base.BaseActivity
import com.tracki.ui.rides.NewsDetailsActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
        val apiService =
            ApiClient.getClient(context =context)?.create(ApiService::class.java)

        // Fetching all news
            apiService?.fetchAllNews("us", "3952dfc43da040d7a08cd5e8400c5d11")!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<NewsResponse> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                   // disposable = d
                }

                override fun onSuccess(newsResponse: NewsResponse) { // Receive all news
                    newsResp = newsResponse
                    mNewsAdapter.setList(newsResp!!.articles!!)

                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })


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


//        val retrofit: Retrofit =  Retrofit.Builder().baseUrl("https://www.newsapi.org/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();

      //  val apiService = ApiClient.getClient(context =context)?.create(ApiService::class.java)

//        val apiService:ApiService  = retrofit.create(ApiService::class.java)

//        val call:Call<NewsResponse>  = apiService!!.fetchAllNews("us","3952dfc43da040d7a08cd5e8400c5d11")!!;
//
//        call.enqueue(object : Callback<NewsResponse> {
//
//            override fun onResponse(call:Call<NewsResponse>, response:Response<NewsResponse>) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(context, "Not successful!", Toast.LENGTH_LONG).show()
//                }
//                newsResp = response.body()
//                mNewsAdapter.setList(newsResp!!.articles!!)
//
//
//            }
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
//
//            }
//    })
    }




    override fun onClickItem(pos:Int) {
        openNewsActivity(pos);
    }

    fun openNewsActivity(pos:Int)
    {
        val intent = NewsDetailsActivity.newIntent(this)
        val bundle = Bundle()
        bundle.putSerializable("NewsResponse", newsResp)
        bundle.putString("Pos", pos.toString())
        intent.putExtras(bundle)
        startActivity(intent)
    }



    companion object {
        private const val TAG = "NewsActivity"
        fun newIntent(context: Context) = Intent(context, NewsActivity::class.java)
    }
}
