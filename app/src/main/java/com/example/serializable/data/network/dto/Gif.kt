package com.example.serializable.data.network.dto

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


@Serializable
data class Gif(var id: String = "", var images: Images = Images(Original())) {
    companion object {
        //        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Gif {
            Log.d("toObject", stringValue)
            return json.parse(serializer(), stringValue)
        }
    }
}
