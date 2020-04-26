package com.example.serializable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity2 : AppCompatActivity() {
    private val gifApi: GifApi = GifApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val responseText = findViewById<TextView>(R.id.responseTxt)
//        findViewById<Button>(R.id.requestButton).setOnClickListener {
//
//            restApi.AsyncGetRequest(this)
//
//            runOnUiThread {
//                responseText.text = " Response"
//                responseText.textSize = 26F
//            }
//        }
    }
}
