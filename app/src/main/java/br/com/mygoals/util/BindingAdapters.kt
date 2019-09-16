package br.com.mygoals.util

import android.os.Build
import android.text.Html
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

@BindingAdapter("htmlText")
fun loadHtmlText(textView: TextView, htmlText: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        textView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        textView.text = Html.fromHtml(htmlText)
    }
}

@BindingAdapter("timestamp")
fun loadFormattedDate(textView: TextView, timestamp: String) {
    val serverUtcDatePattern = "yyyy-MM-dd'T'HH:mm:ss.025'Z'"
    val listDatePattern = "MMMM dd, yyyy - HH:mm"
    val inputDateFormat = SimpleDateFormat(serverUtcDatePattern, Locale.UK)
    val outputDateFormat = SimpleDateFormat(listDatePattern, Locale.UK)
    val now = Calendar.getInstance().time
    val context = textView.context

    try {
        val date = inputDateFormat.parse(timestamp)
        val diffInMillisec = now.time - date.time
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec)
        val diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec)
        val diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec)

        when {
            (diffInSec <= 60) -> textView.text =
                context.getString(R.string.list_item_feed_timestamp_seconds, diffInSec)
            (diffInMin <= 60) -> textView.text =
                context.getString(R.string.list_item_feed_timestamp_minutes, diffInMin)
            (diffInHours <= 24) -> textView.text =
                context.getString(R.string.list_item_feed_timestamp_hours, diffInHours)
            else -> textView.text = outputDateFormat.format(date)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        textView.text = timestamp
    }
}
