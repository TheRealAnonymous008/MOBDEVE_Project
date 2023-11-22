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
    var dateTimeFinished : Timestamp? = null,

) : Serializable {
    var progress : Long = 0

    fun getDurationAsString(): String {
        if (datetimeFrom == null && dateTimeTo == null)
            return ""

        if (dateTimeTo == null)
            return ""

        return getTimeFormattedString(datetimeFrom!!.time - dateTimeTo!!.time)
    }

    fun getTimePassedAsString() : String {
        if (dateTimeTo == null || datetimeFrom == null)
            return ""

        val timeLeft = ((dateTimeTo!!.time - datetimeFrom!!.time)  * (getNormalizedProgress())).toLong()
        return getTimeFormattedString(timeLeft)
    }

    fun addProgress(x : Long) {
        progress += x
    }

    fun setFinished() {
        dateTimeFinished = getCurrentTimeStamp()
    }

    fun getNormalizedProgress() : Float {
        val to = dateTimeTo
        val from = datetimeFrom

        if (from != null && to != null) {
            return 1000 *  progress / (1.0f * (to.time - from.time))
        }
        return 1.0f
    }

    private fun getTimeFormattedString(time: Long) : String{
        val durationInMillis = time

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

    fun isValid(): Boolean {
        if (datetimeFrom == null || dateTimeTo == null)
            return true

        return datetimeFrom!!.time < dateTimeTo!!.time
    }

}

fun formatTimestampDate(timestamp: Timestamp?): String {
    if (timestamp == null){
        return "No Date"
    }
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(timestamp)
}

fun formatTimestampTime(timestamp: Timestamp?): String {
    if (timestamp == null){
        return "No Time"
    }
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(timestamp)
}

fun getTimeStampDate(timestamp: Timestamp?, defaultValue : Timestamp) : Date {
    if (timestamp == null){
        return Date(defaultValue.time)
    }
    return Date(timestamp.time)
}