package com.example.serializable

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Meta(val status: Int, val msg: String, val response_id: String) {
    companion object {
//        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Meta {
            return json.parse(Meta.serializer(), stringValue)
        }

        fun toJson(field: Meta): String {
            // Обратите внимание, что мы вызываем Serializer, который автоматически сгенерирован из нашего класса
            // Сразу после того, как мы добавили аннотацию @Serializer
            return json.stringify(Meta.serializer(), field)
        }
    }
}


@Serializable
data class Original(var url: String = "" ){
    companion object {
        //        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Original {
            Log.d("toObject", stringValue)
            return json.parse(Original.serializer(), stringValue)
        }
    }
}

@Serializable
data class Images(var original: Original ){
    companion object {
        //        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Images {
            Log.d("toObject", stringValue)
            return json.parse(Images.serializer(), stringValue)
        }
    }
}


@Serializable
data class Gif(var id: String = "", var url: String = "" ) {
    companion object {
//        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Gif {
            Log.d("toObject", stringValue)
            return json.parse(Gif.serializer(), stringValue)
        }
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Gif.serializer(), this)
    }
}

@Serializable
data class Body(var data: List<Gif>, var meta: Meta) {
    companion object {
        private val json = Json(JsonConfiguration(ignoreUnknownKeys=true))
        fun toObject(stringValue: String): Body {
            Log.d(" Body toObject", stringValue)
            return json.parse(Body.serializer(), stringValue)
        }
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Body.serializer(), this)
    }
}


@Serializable
data class Data2(val a: Int, val b: String = "42")


fun main() {
    // Json also has .Default configuration which provides more reasonable settings,
    // but is subject to change in future versions
    val json = Json(JsonConfiguration.Stable)
    // serializing objects
    val jsonData = json.stringify(Data2.serializer(), Data2(42))
    // serializing lists
    val jsonList =
        json.stringify(Data2.serializer().list, listOf(Data2(42), Data2(43, "45"), Data2(44, "46")))
    println(jsonData) // {"a": 42, "b": "42"}
    println(jsonList) // [{"a": 42, "b": "42"}]

    // parsing data back
    val obj =
        json.parse(Data2.serializer(), """{"a":42}""") // b is optional since it has default value
    println(obj) // Data(a=42, b="42")
}