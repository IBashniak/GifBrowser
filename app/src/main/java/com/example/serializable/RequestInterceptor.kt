package com.example.serializable


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("intercept", request.toString())
        return chain.proceed(request)
    }
}
