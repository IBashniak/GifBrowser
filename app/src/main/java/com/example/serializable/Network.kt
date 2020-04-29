package com.example.serializable

import android.app.Activity
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.serialization.InternalSerializationApi
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.coroutines.*
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GifApi {
    companion object {
        private const val URL = "https://api.giphy.com/v1/"
        private const val API_KEY = "7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k"
        private const val TIMEOUT_IN_SECONDS = 2
        private const val SEARCH_END_POINT = "gifs/search"
        private const val SEARCH_STRING = "Bojack Horse"
        private const val SEARCH_LIMITS = 15
    }

    lateinit var client: OkHttpClient

    init {
        client =
            OkHttpClient.Builder().connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .build()
    }
    
    /* Get route used to retrieve cat images, limit is the number of cats item */

    fun getGifs(limit: Int)
            : Deferred<List<Gif>> {
        return GlobalScope.async {
            val TAG = "getGifs"
            val urlBuilder =
                HttpUrl.parse(URL + SEARCH_END_POINT)?.newBuilder()
            urlBuilder?.addQueryParameter("api_key", API_KEY)
            urlBuilder?.addQueryParameter("q", SEARCH_STRING)
            urlBuilder?.addQueryParameter("limit", SEARCH_LIMITS.toString())

            var url = urlBuilder?.build().toString()
            val request: Request = Request.Builder()
                .url(url)
                .build()
            Log.d("$TAG url=", url)
            var result: List<Gif> = listOf(Gif())

                val response = client.newCall(request).execute()
                val resp = response?.body()?.string()
                Log.d("$TAG '0' ", "body ${resp}  ")
                Log.d("$TAG", "message ${response?.message().toString()}  ")
                Log.d(
                    "$TAG",
                    "networkResponse ${response?.networkResponse().toString()}  "
                )
                Log.d(
                    "$TAG",
                    "isSuccessful ${response?.isSuccessful.toString()}  "
                )

                val data = Gif.toObject(resp.toString())
                Log.d("$TAG !!! data=", "call ${data.toString()}  ")
                if (resp != null) {
                    val body = Body.toObject(resp)
                    result = body.data

                    Log.d(
                        "body.data[0]",
                        "${body.data[0].url} size = ${body.data.size} ${result.size}"
                    )
                }
                return@async result
        }
    }
}