package com.example.serializable.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.serializable.data.network.dto.GifDTO

class GifAdapter(private val glideRequestManager: RequestManager) :
    RecyclerView.Adapter<GifViewHolder>() {
    private var items: List<GifDTO> = listOf()
    val TAG = "GifAdapter"

    init {
        Log.d(TAG, "GifAdapter CTRL")
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): GifViewHolder {
        return GifViewHolder.create(viewGroup, glideRequestManager)
    }

    override fun onBindViewHolder(
        @NonNull photoViewHolder: GifViewHolder,
        position: Int
    ) {
        Log.d(TAG, "onBindViewHolder")
        val gif: GifDTO = items[position]
        photoViewHolder.bindItem(gif)
    }

    fun replaceItems(@NonNull _items: List<GifDTO?>?) {
        if (_items == null)
            items = listOf()
        else
            items = _items as List<GifDTO>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${items.size}")
        return items.size
    }

}
