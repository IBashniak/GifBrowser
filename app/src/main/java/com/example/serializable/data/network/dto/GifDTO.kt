package com.example.serializable.data.network.dto

import android.util.Log
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


@Serializable
data class GifDTO(var id: String = "", @SerialName("images") var imagesDTO: ImagesDTO = ImagesDTO(OriginalDTO())) {
    val url = imagesDTO.originalDTO.url

    companion object {
        //        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): GifDTO {
            Log.d("toObject", stringValue)
            return json.parse(serializer(), stringValue)
        }
    }
}
