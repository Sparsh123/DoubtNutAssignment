package com.example.newsapplication.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.newsapplication.ui.base.BaseViewModel



class NewsViewModel :
        BaseViewModel<NewsNavigator>() {

    inner class GetMyEarnings(private val earningRequest: MyEarningRequest) : ApiCallback {

        override fun onResponse(result: Any?, error: APIError?) {
            navigator.handleResponse(this, result, error)
        }

        override fun hitApi() {
            val api = TrackiApplication.getApiMap()[ApiType.MY_EARNINGS]!!
            dataManager.getMyEarnings(this, httpManager, earningRequest, api)
        }

        override fun isAvailable(): Boolean {
            return true
        }

        override fun onNetworkErrorClose() {
        }

        override fun onRequestTimeOut(callBack: ApiCallback) {
            navigator.showTimeOutMessage(callBack)
        }

        override fun onLogout() {
        }
    }
}