package com.example.serializable

import android.util.Log
import com.example.serializable.data.network.dto.BodyDTO
import com.example.serializable.data.network.dto.GifDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class GifApi {
    companion object {
        private const val URL = "https://api.giphy.com/v1/"
        private const val API_KEY = "7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k"
        private const val TIMEOUT_IN_SECONDS = 2
        private const val SEARCH_END_POINT = "gifs/search"
        private const val SEARCH_STRING = "Bojack Horse"
        private const val SEARCH_LIMITS = 45
    }

    private var client: OkHttpClient



    init {
        client =
            OkHttpClient.Builder().connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .build()
    }


    fun getGifsAsync(search: String = SEARCH_STRING, limit: Int = SEARCH_LIMITS) =
        GlobalScope.async {
            val TAG = "getGifsAsync"
            val urlBuilder =
                (URL + SEARCH_END_POINT).toHttpUrlOrNull()?.newBuilder()
            urlBuilder?.apply {
                addQueryParameter("api_key", API_KEY)
                addQueryParameter("q", search)
                addQueryParameter("limit", limit.toString())
            }
            val url = urlBuilder?.build().toString()
            Log.d(TAG, url)
            val request: Request = Request.Builder()
                .url(url)
                .build()
            Log.d("$TAG url=", url)
            var result: List<GifDTO> = listOf(
                GifDTO()
            )

            val response = client.newCall(request).execute()
            val resp = response.body?.string()
            Log.d("$TAG '0' ", "body $resp  ")
            Log.d(TAG, "message ${response.message}  ")
            Log.d(TAG, "networkResponse ${response.networkResponse.toString()}  "
            )
            Log.d( TAG, "isSuccessful ${response.isSuccessful}  ")

            val data = GifDTO.toObject(resp.toString())
            Log.d("$TAG !!! data=", "call $data  ")
            if (resp != null) {
                val body = BodyDTO.toObject(resp)
                result = body.data

                Log.d(
                    "body.data[0]",
                    "${body.data[0].imagesDTO.originalDTO.url} size = ${body.data.size} ${result.size}"
                )
            }
            return@async result
        }
}