package com.example.serializable.ui.adapter
//
//import android.R
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import kotlinx.serialization.json.Json.Default.context
//
//
//class GifAdapter(val context: Context): RecyclerView.Adapter<GifAdapter.GifViewHolder> {
//    // Предоставляет прямую ссылку на каждый View-компонент
//    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
//    val inflater = LayoutInflater.from(context);
//    internal inner class GifViewHolder(itemView: View) : ViewHolder(itemView) {
//        // Ваш ViewHolder должен содержать переменные для всех
//        // View-компонентов, которым вы хотите задавать какие-либо свойства
//        // в процессе работы пользователя со списком
//        private val userImageView: ImageView
//        private val nameTextView: TextView
//
//
//        // Мы также создали конструктор, который принимает на вход View-компонент строкИ
//        // и ищет все дочерние компоненты
//        init {
//            userImageView = itemView.findViewById(R.id.image)
//            nameTextView = itemView.findViewById(R.id.name)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
//        val view: View = inflater.inflate(R.layout.list_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//}