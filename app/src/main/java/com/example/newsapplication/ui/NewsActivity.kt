package com.example.newsapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityNewsBinding
import com.example.newsapplication.ui.base.BaseActivity
import com.google.gson.Gson

import java.util.*
import javax.inject.Inject



class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>(),
    NewsNavigator, NewsAdapter.OnItemClickListener {

    @Inject
    lateinit var mNewsViewModel: NewsViewModel
    @Inject
    lateinit var mNewsAdapter: NewsAdapter
    @Inject

    private lateinit var mActivityNewsBinding: ActivityNewsBinding
    private lateinit var toolbar: Toolbar


    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_news
    override fun getViewModel() = mNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityNewsBinding = viewDataBinding
        mNewsViewModel.navigator = this
        //set listener
        mNewsAdapter.setOnItemListener(this)
        toolbar = mActivityNewsBinding.toolbar
        setToolbar(toolbar, getString(R.string.my_news))

        //set Adapter
        mActivityNewsBinding.rvEarningList.adapter = mNewsAdapter
        //start time

    }

    override fun onClickItem(date: Long) {
        openNewsFragment();
    }

    fun openNewsFragment()
    {

    }



    companion object {
        private const val TAG = "NewsActivity"
        fun newIntent(context: Context) = Intent(context, NewsActivity::class.java)
    }
}
