package br.com.mygoals.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mygoals.base.view.DataBindingAdapter

@BindingAdapter("adapter", "data")
fun <T> loadData(recyclerView: RecyclerView, adapter: DataBindingAdapter<T>, data: List<T>) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = adapter
    }
    adapter.submitList(data)
}
