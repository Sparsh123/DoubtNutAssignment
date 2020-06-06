package com.tracki.ui.base

import androidx.annotation.NonNull
import com.tracki.data.network.APIError
import com.tracki.data.network.ApiCallback


interface BaseNavigator {
    fun handleResponse(callback: ApiCallback, result: Any?, error: APIError?)
    fun showTimeOutMessage(@NonNull callback: ApiCallback)
}