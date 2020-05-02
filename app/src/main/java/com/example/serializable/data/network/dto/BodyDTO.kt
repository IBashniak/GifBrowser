package com.example.serializable.data.network.dto

import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class BodyDTO(var data: List<GifDTO>,  @SerialName("meta") var metaDTO: MetaDTO) {
    companion object {
//        @OptIn(UnstableDefault::class)
        private val json = Json(
            JsonConfiguration(ignoreUnknownKeys = true)
        )
        fun toObject(stringValue: String): BodyDTO {
            Log.d(" Body toObject", stringValue)
            return json.parse(serializer(), stringValue)
        }
    }
}