package com.example.serializable.ui.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.serializable.data.network.dto.GifDTO

//import ru.alexbykov.networksample.data.network.dto.GifDTO;
class PhotosAdapter(private val glideRequestManager: RequestManager) :
    RecyclerView.Adapter<GifViewHolder>() {
    private var items: List<GifDTO> = listOf()

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): GifViewHolder {
        return GifViewHolder.create(viewGroup, glideRequestManager)
    }

    override fun onBindViewHolder(
        @NonNull photoViewHolder: GifViewHolder,
        position: Int
    ) {
        val gif: GifDTO = items[position]
        photoViewHolder.bindItem(gif)
    }

    fun replaceItems(@NonNull _items: List<GifDTO?>?) {
        if(_items == null)
            items= listOf()
        else
            items = _items as List<GifDTO>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
