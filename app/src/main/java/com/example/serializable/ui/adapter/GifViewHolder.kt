package com.example.serializable.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

import com.example.serializable.R
import com.example.serializable.data.network.dto.GifDTO

class GifViewHolder private constructor( @NonNull itemView: View,    private val imageLoader: RequestManager) : ViewHolder(itemView) {
    private var ivGif: ImageView? = null
    private var progressBar: ProgressBar? = null
    fun bindItem(@NonNull gif: GifDTO) {
        progressBar!!.visibility = View.VISIBLE
        ivGif?.let {
            imageLoader.load(gif.url)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar!!.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar!!.visibility = View.GONE
                        return false
                    }
                })
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .thumbnail(0.3f)
                .into(it)
        }
    }

    private fun findViews(@NonNull itemView: View) {
//        ivGif = itemView.findViewById(R.id.iv_gif)
//        progressBar = itemView.findViewById(R.id.progress_bar)
    }

    companion object {
        private val LAYOUT: Int = R.layout.list_item
        fun create( @NonNull parent: ViewGroup,  glideRequestManager: RequestManager ): GifViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                LAYOUT,
                parent,
                false
            )
            return GifViewHolder(view, glideRequestManager)
        }
    }

        init {
        findViews(itemView)
        }
    }