package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TimeInfo(
    var datetimeCreated: Timestamp = getCurrentTimeStamp(),
    var datetimeFrom: Timestamp? = null,
    var dateTimeTo: Timestamp? = null,
) : Serializable {
    fun getDurationAsString(): String {
        if (datetimeFrom == null && dateTimeTo == null)
            return ""

        if (dateTimeTo == null)
            return ""

        return getTimeFormattedString()
    }

    fun containsFrom() : Boolean {
        return datetimeFrom != null
    }

    fun containsTo() : Boolean {
        return dateTimeTo != null
    }

    private fun getTimeFormattedString() : String{
        val durationInMillis = dateTimeTo!!.time - datetimeFrom!!.time

        val seconds = durationInMillis / 1000 % 60
        val minutes = durationInMillis / (1000 * 60) % 60
        val hours = durationInMillis / (1000 * 60 * 60) % 24
        val days = durationInMillis / (1000 * 60 * 60 * 24)

        val formattedDuration = StringBuilder()

        if (days > 0) {
            formattedDuration.append("${days}d ")
        }

        if (hours > 0) {
            formattedDuration.append("${hours}h ")
        }

        if (minutes > 0) {
            formattedDuration.append("${minutes}m ")
        }

        if (seconds > 0 || formattedDuration.isEmpty()) {
            formattedDuration.append("${seconds}s")
        }

        return formattedDuration.toString().trim()
    }

}

fun formatTimestampDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(timestamp)
}

fun formatTimestampTime(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("hh:dd a", Locale.getDefault())
    return sdf.format(timestamp)
}