package br.com.mygoals.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mygoals.R
import br.com.mygoals.base.view.DataBindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("adapter", "data")
fun <T> loadData(recyclerView: RecyclerView, adapter: DataBindingAdapter<T>, data: List<T>) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = adapter
    }
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .error(R.color.lightBlue)
        .into(imageView)
}
