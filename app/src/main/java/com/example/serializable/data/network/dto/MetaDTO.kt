package com.example.serializable.data.network.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class MetaDTO(val status: Int, val msg: String, val response_id: String) {
    companion object {
        //        @OptIn(UnstableDefault::class)
        private val json = Json(
            JsonConfiguration(ignoreUnknownKeys = true)
        )
        fun toObject(stringValue: String): MetaDTO {
            return json.parse(serializer(), stringValue)
        }

        fun toJson(field: MetaDTO): String {
            // Обратите внимание, что мы вызываем Serializer, который автоматически сгенерирован из нашего класса
            // Сразу после того, как мы добавили аннотацию @Serializer
            return json.stringify(serializer(), field)
        }
    }
}