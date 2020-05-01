package com.example.serializable

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi

class MainActivity : AppCompatActivity() {
    private val gifApi: GifApi = GifApi()
//    var gifList: List<Gif>?=null

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.RequestButton)
        val image = findViewById<ImageView>(R.id.gifImageView)
        val activity: Activity = this

        btn.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO){
                val result = GifRepositoryImpl(gifApi).getGifList()
                withContext(Dispatchers.Main) {
                Log.d("Main result.data", "$result ")
                    when (result) {
                        is UseCaseResult.Success -> {
                            val img= result.data.random()
                            Glide.with(activity)
                                .load(img.images.original.url)
                                .into(image);
//                            image.adjustViewBounds =wrap_content
//                            (img.images.original.width, img.images.original.height)
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

//            if (gifList !=null) {
//                Glide.with(activity)
//                    .load(gifList?.random()?.images?.original?.url)
//                    .into(image);
//            }
        }

    }
}
