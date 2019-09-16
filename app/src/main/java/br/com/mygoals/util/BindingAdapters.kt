package br.com.mygoals.util

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mygoals.R
import br.com.mygoals.base.api.models.Goal
import br.com.mygoals.base.view.DataBindingAdapter
import com.bumptech.glide.Glide
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

@BindingAdapter("adapter", "data")
fun <T> loadData(recyclerView: RecyclerView, adapter: DataBindingAdapter<T>, data: List<T>) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = adapter
    }
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .error(R.color.lightBlue)
        .into(imageView)
}

@BindingAdapter("errorMessage")
fun loadErrorMessage(textView: TextView, error: Throwable) {
    val context = textView.context
    val errorMessage =
        when (error) {
            is IOException -> context.getString(R.string.error_io_exception)
            is HttpException -> context.getString(R.string.error_http_exception)
            is SocketTimeoutException -> context.getString(R.string.error_timeout_exception)
            else -> context.getString(R.string.error_unknown)
        }
    textView.text = errorMessage
}

@BindingAdapter("goal")
fun loadCurrentBalance(progressBar: ProgressBar, goal: Goal?) {
    goal?.let {
        progressBar.max = goal.targetAmount?.toInt() ?: goal.currentBalance.toInt()
        progressBar.progress = goal.currentBalance.toInt()
    }
}