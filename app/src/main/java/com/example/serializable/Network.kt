package com.example.serializable

import android.app.Activity
import android.util.Log
import android.widget.TextView
import kotlinx.serialization.InternalSerializationApi
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class RestApi {
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

    @InternalSerializationApi
    fun asyncGetRequest(act: Activity) {
        val urlBuilder =
            HttpUrl.parse(URL + SEARCH_END_POINT)?.newBuilder()
        urlBuilder?.addQueryParameter("api_key", API_KEY)
        urlBuilder?.addQueryParameter("q", SEARCH_STRING)
        urlBuilder?.addQueryParameter("limit", SEARCH_LIMITS.toString())

        var url = urlBuilder?.build().toString()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        Log.d("AsyncGetRequest url=", url)

        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            //            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                val resp = response?.body()?.string()
                Log.d("AsyncGetRequest '0' ", "body ${resp}  ")
                Log.d("AsyncGetRequest", "message ${response?.message().toString()}  ")
                Log.d("AsyncGetRequest", "networkResponse ${response?.networkResponse().toString()}  ")
                Log.d("AsyncGetRequest", "isSuccessful ${response?.isSuccessful.toString()}  ")

                Log.d("AsyncGetRequest", "call ${call.toString()}  ")

                val data = Data.toObject(resp.toString())
                Log.d("AsyncGetRequest !!! data=", "call ${data.toString()}  ")
                if (resp != null) {
                    val body = Body.toObject(resp)
                    body.data.forEach {
                        Log.d("body.data", "${it.url} ")
                    }
                    Log.d("body.data[0]", "${body.data[0].url} size = ${body.data.size}")
                }

                act.runOnUiThread {
                    val responseText = act.findViewById<TextView>(R.id.ResponseText)
                    responseText.text = response?.toString()
                    responseText.textSize = 26F
                    Log.d("AsyncGetRequest ___", response?.toString())
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("AsyncGetRequest onFailure", call?.toString())
                Log.d("AsyncGetRequest e", e.toString())
            }
        })
    }

}