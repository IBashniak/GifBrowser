package com.example.serializable.data.network.dto

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Body(var data: List<Gif>, var meta: Meta) {
    companion object {
        private val json = Json(
            JsonConfiguration(ignoreUnknownKeys = true)
        )
        fun toObject(stringValue: String): Body {
            Log.d(" Body toObject", stringValue)
            return json.parse(serializer(), stringValue)
        }
    }
}