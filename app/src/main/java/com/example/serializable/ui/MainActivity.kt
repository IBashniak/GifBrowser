package com.example.serializable.ui

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.serializable.GifApi
import com.example.serializable.GifRepositoryImpl
import com.example.serializable.R
import com.example.serializable.UseCaseResult
import com.example.serializable.ui.adapter.GifAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi


class MainActivity : AppCompatActivity() {
    private val gifApi: GifApi = GifApi()
    private var activity: Activity = this
    private val LAYOUT = R.layout.activity_main
    val TAG = "MainActivity "

    val DEFAULT_SEARCH_REQUEST = "Bojack Horseman "

    private lateinit var toolbar: Toolbar
    private lateinit var rvPhotos: RecyclerView
    private lateinit var btnTryAgain: Button
    private lateinit var viewError: View
    private lateinit var viewLoading: View
    private lateinit var viewNoData: View
    private lateinit var tvError: TextView
    private lateinit var searchView: SearchView
    private lateinit var gifAdapter: GifAdapter

    private fun findViews() {
        Log.d(TAG, "findViews")
        rvPhotos = findViewById<RecyclerView>(R.id.rv_photos)
        toolbar = findViewById(R.id.toolbar)
        btnTryAgain = findViewById(R.id.btn_try_again)
        viewError = findViewById(R.id.lt_error)
        viewLoading = findViewById(R.id.lt_loading)
        viewNoData = findViewById(R.id.lt_no_data)
        tvError = findViewById(R.id.tv_error)
    }


    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
        setupUx()
        GlobalScope.launch(Dispatchers.IO) {
            val gifs = GifRepositoryImpl(gifApi).getGifList(DEFAULT_SEARCH_REQUEST)
        }
    }

    private fun setupRecyclerViews() {
        Log.d(TAG, "setupRecyclerViews")
        gifAdapter = GifAdapter(Glide.with(this))
        rvPhotos.layoutManager = LinearLayoutManager(this)
        rvPhotos.adapter = gifAdapter
    }
    private fun setupSearchView() {
        toolbar.inflateMenu(R.menu.menu_search)
        val item = toolbar.menu.findItem(R.id.menu_item_search)
        searchView = item.actionView as SearchView
    }
    private fun setupUi() {
        Log.d(TAG, "setupUi")
        findViews()
        toolbar.setTitle(R.string.toolbar_search_tittle)
        setupRecyclerViews()
        setupSearchView()
    }
    private fun setupUx() {
        Log.d(TAG, "setupUx")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, "onQueryTextSubmit")
                validateAndLoadGifs(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //we can use this callback if we want to make request when text change
                return false
            }
        })
        btnTryAgain.setOnClickListener { v: View? -> onClickTryAgain() }
    }

    private fun onClickTryAgain() {
        Log.d(TAG, "onClickTryAgain")
        val query = searchView.query.toString()
        if (query.isEmpty()) {
            validateAndLoadGifs(DEFAULT_SEARCH_REQUEST)
        } else {
            validateAndLoadGifs(query)
        }
    }

    private fun validateAndLoadGifs(query: String) {
        Log.d(TAG, "validateAndLoadGifs")
        if (QueryValidator.isValid(query)) {
            GlobalScope.launch(Dispatchers.IO) {
                val gifs = GifRepositoryImpl(gifApi).getGifList(query)
                runOnUiThread {
                    when (gifs) {
                        is UseCaseResult.Success -> {
                            gifAdapter.replaceItems(gifs.data)
                            val img = gifs.data.random()



//                            val ivGif = activity.findViewById<ImageView>(R.id.test_imageView)
//                            if (ivGif ==null)
//                                Log.d(TAG, "ivGif ==nul !!!  ${ivGif.toString()}")
//                            ivGif?.let {
//                                Log.d(TAG, "$img.url ${ivGif.toString()}")
//                                Glide.with(ivGif!!)
//                                    .load("https://koenig-media.raywenderlich.com/uploads/2019/05/Screenshot_1557010833-281x500.png")
//                                    .listener(object : RequestListener<Drawable> { //9
//                                        override fun onLoadFailed(
//                                            e: GlideException?, model: Any?, target: Target<Drawable>?,
//                                            isFirstResource: Boolean
//                                        ): Boolean {
//                                            if (e != null) {
//                                                Log.d(
//                                                    TAG,
//                                                    "onLoadFailed ${e.message}  ${e.cause}  $e.logRootCauses(TAG)"
//                                                )
//                                            }
//                                            return false
//                                        }
//
//                                        override fun onResourceReady(
//                                            resource: Drawable?, model: Any?, target: Target<Drawable>?,
//                                            dataSource: DataSource?, isFirstResource: Boolean
//                                        ): Boolean {
//
//                                            Log.d(TAG, "onResourceReady ")
//                                            return false
//                                        }
//                                    })
//                                    .into(it)
//
//
//                            }
                        }
                        is UseCaseResult.Error -> {
                            Log.d("Main result.error", "${gifs.exception} ")
                        }
                    }
                }

            }
        }
    }


}

