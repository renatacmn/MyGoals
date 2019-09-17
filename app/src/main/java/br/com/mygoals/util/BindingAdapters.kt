package br.com.mygoals.util

import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mygoals.R
import br.com.mygoals.base.repository.models.FeedItem
import br.com.mygoals.base.repository.models.Goal
import br.com.mygoals.base.view.DataBindingAdapter
import com.bumptech.glide.Glide
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
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

@BindingAdapter("goalProgress")
fun loadCurrentBalance(progressBar: ProgressBar, goal: Goal?) {
    goal?.let {
        progressBar.max = goal.targetAmount?.toInt() ?: goal.currentBalance.toInt()
        progressBar.progress = goal.currentBalance.toInt()
    }
}

@BindingAdapter("thisWeekSum")
fun loadThisWeeksSum(textView: TextView, list: List<FeedItem>) {
    val now = Calendar.getInstance().time
    val context = textView.context
    var sum = 0.0

    for (feedItem in list) {
        val diffInMillis = now.time - feedItem.timestamp.time
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        if (diffInDays <= 7) {
            sum += feedItem.amount
        }
    }
    textView.text = context.getString(R.string.price, sum)
}

@BindingAdapter("htmlText")
fun loadHtmlText(textView: TextView, htmlText: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        textView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        textView.text = Html.fromHtml(htmlText)
    }
}

@BindingAdapter("feedTimestamp")
fun loadFormattedDate(textView: TextView, timestamp: Date) {
    val listDatePattern = "MMMM dd, yyyy - HH:mm"
    val outputDateFormat = SimpleDateFormat(listDatePattern, Locale.UK)
    val now = Calendar.getInstance().time
    val context = textView.context

    val diffInMillis = now.time - timestamp.time
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)

    when {
        (diffInSec <= 60) -> textView.text =
            context.getString(R.string.list_item_feed_timestamp_seconds, diffInSec)
        (diffInMin <= 60) -> textView.text =
            context.getString(R.string.list_item_feed_timestamp_minutes, diffInMin)
        (diffInHours <= 24) -> textView.text =
            context.getString(R.string.list_item_feed_timestamp_hours, diffInHours)
        else -> textView.text = outputDateFormat.format(timestamp)
    }
}
