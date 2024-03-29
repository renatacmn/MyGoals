package br.com.mygoals.base.repository.api

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class CustomDateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.UK)

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm:ss.025'Z'")
    }

}
