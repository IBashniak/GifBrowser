package com.example.serializable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.serialization.InternalSerializationApi

class MainActivity : AppCompatActivity() {
    private val restApi: RestApi = RestApi()

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.RequestButton)
        btn.setOnClickListener {
            val textView = findViewById<TextView>(R.id.ResponseText)
            textView.text = "FFFFFFFFFFFFFF"
            restApi.asyncGetRequest(this)
        }

    }
}
