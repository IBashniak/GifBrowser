package com.example.serializable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi

class MainActivity : AppCompatActivity() {
    private val gifApi: GifApi = GifApi()

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.RequestButton)
        btn.setOnClickListener {
            val textView = findViewById<TextView>(R.id.ResponseText)
            textView.text = "FFFFFFFFFFFFFF"


            GlobalScope.launch(Dispatchers.IO){
                val result = GifRepositoryImpl(gifApi).getGifList()
                withContext(Dispatchers.Main) {
                Log.d("Main result.data", "$result ")
                    when (result) {
                        is UseCaseResult.Success -> {
                            result.data.forEach {
                                Log.d("Main result.data", "${it.images.original} ")
                            }
                        }
                        is UseCaseResult.Error -> {
                            Log.d("Main result.error", "${result.exception} ")
                        }
                    }
                }
            }
        }

    }
}
