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
data class Data(var id: String = "", var url: String = "") {
    companion object {
//        @OptIn(UnstableDefault::class)
        private val json = Json(JsonConfiguration(ignoreUnknownKeys = true))
        fun toObject(stringValue: String): Data {

            val stringValue0 = "{\n" +
                    "   \"type\":\"gif\",\n" +
                    "   \"id\":\"YaZgr3Nj9DDI4\",\n" +
                    "   \"url\":\"https:\\/\\/giphy.com\\/gifs\\/horse-YaZgr3Nj9DDI4\",\n" +
                    "   \"slug\":\"horse-YaZgr3Nj9DDI4\",\n" +
                    "   \"bitly_gif_url\":\"https:\\/\\/gph.is\\/1oliqdd\",\n" +
                    "   \"bitly_url\":\"https:\\/\\/gph.is\\/1oliqdd\",\n" +
                    "   \"embed_url\":\"https:\\/\\/giphy.com\\/embed\\/YaZgr3Nj9DDI4\",\n" +
                    "   \"username\":\"\",\n" +
                    "   \"source\":\"https:\\/\\/www.gifbay.com\\/gif\\/with_horse-124435\\/\",\n" +
                    "   \"title\":\"horse GIF\",\n" +
                    "   \"rating\":\"pg-13\",\n" +
                    "   \"content_url\":\"\",\n" +
                    "   \"source_tld\":\"www.gifbay.com\",\n" +
                    "   \"source_post_url\":\"https:\\/\\/www.gifbay.com\\/gif\\/with_horse-124435\\/\",\n" +
                    "   \"is_sticker\":0,\n" +
                    "   \"import_datetime\":\"2014-04-01 11:50:45\",\n" +
                    "   \"trending_datetime\":\"0000-00-00 00:00:00\",\n" +
                    "   \"images\":{\n" +
                    "\n" +
                    "   }\n" +
                    "}"
//        val stringValue1 = "{\"protocol\":\"h2\", \"code\":200, \"message\":\"\", \"url\":\"https/://api.giphy.com/v1/gifs/search?api_key=7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k&q=Horse\"}"
            val stringValue1 = stringValue.replace("Response", "")
            Log.d("toObject", stringValue1)


            return json.parse(Data.serializer(), stringValue0)
        }
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Data.serializer(), this)
    }
}

@Serializable
data class Body(var data: List<Data>, var meta: Meta) {
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