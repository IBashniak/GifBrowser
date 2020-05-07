package com.example.serializable.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.serializable.R
import com.example.serializable.data.network.dto.GifDTO
import com.example.serializable.data.network.dto.url

class GifViewHolder private constructor( @NonNull itemView: View,    private val imageLoader: RequestManager) : ViewHolder(itemView) {
    private var ivGif: ImageView? = null
    private var progressBar: ProgressBar? = null
    val TAG = "GifViewHolder"
    init{
        Log.d(TAG, "GifViewHolder ")
    }

    fun bindItem(@NonNull gif: GifDTO) {
        Log.d(TAG, "bindItem  ${gif.url}")
        progressBar!!.visibility = View.VISIBLE
        if (ivGif ==null)
            Log.d(TAG, "ivGif ==nul !!!  ${ivGif.toString()}")
        ivGif?.let {
             Glide.with(ivGif!!)
                 .load(gif.url)
                .listener (object : RequestListener<Drawable> { //9
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        if (e != null) {
                            Log.d(TAG, "onLoadFailed ${e.message}  ${e.cause}  $e.logRootCauses(TAG)")
                        }
                        progressBar!!.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Log.d(TAG, "onResourceReady ")
                        progressBar!!.visibility = View.GONE
                        return false
                    }
                })
                .into(it)
        }
    }

    private fun findViews(@NonNull itemView: View) {
        Log.d(TAG, "findViews ")
        ivGif = itemView.findViewById(R.id.iv_gif)
        progressBar = itemView.findViewById(R.id.progress_bar)
    }

    companion object {
        private val LAYOUT: Int = R.layout.list_item
        fun create( @NonNull parent: ViewGroup,  glideRequestManager: RequestManager ): GifViewHolder {
            val view = LayoutInflater.from(parent.context).inflate( LAYOUT, parent,false)
            return GifViewHolder(view, glideRequestManager)
        }
    }

        init {
        findViews(itemView)
        }
    }