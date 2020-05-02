package com.example.serializable.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.serializable.GifApi
import com.example.serializable.GifRepositoryImpl
import com.example.serializable.R
import com.example.serializable.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

class MainActivity : AppCompatActivity() {
    private val gifApi: GifApi = GifApi()
    private var activity: Activity = this
//    var gifList: List<Gif>?=null


    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.RequestButton)
        val image = findViewById<ImageView>(R.id.gifImageView)
        val recyclerView = findViewById<RecyclerView>(R.id.gifRecyclerView);

        showGif()
        btn.setOnClickListener {
            showGif()
        }
    }

    private fun showGif() = GlobalScope.launch(Dispatchers.IO) {
        val gifs = GifRepositoryImpl(gifApi).getGifList()
        runOnUiThread {
            Log.d("Main result.data", "$gifs ")
            val image = findViewById<ImageView>(R.id.gifImageView)
            when (gifs) {
                is UseCaseResult.Success -> {
                    val img = gifs.data.random()
                    Glide.with(activity)
                        .load(img.imagesDTO.originalDTO.url)
                        .into(image);
                    gifs.data.forEach {
                        Log.d("Main result.data", "${it.imagesDTO.originalDTO} ")
                    }
                }
                is UseCaseResult.Error -> {
                    Log.d("Main result.error", "${gifs.exception} ")
                }
            }
        }
    }
}

