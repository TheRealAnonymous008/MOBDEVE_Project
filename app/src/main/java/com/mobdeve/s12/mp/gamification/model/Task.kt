package com.mobdeve.s12.mp.gamification.model

import java.sql.Timestamp

data class TimeInfo(
    val datetimeCreated: Timestamp = Timestamp(System.currentTimeMillis()),
    val datetimeFrom: Timestamp? = Timestamp(System.currentTimeMillis()),
    val dateTimeTo: Timestamp? = null,
){
    fun getDurationAsString(): String {
        if (datetimeFrom == null && dateTimeTo == null)
            return ""

        if (dateTimeTo == null)
            return ""

        return getTimeFormattedString()
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

data class Task(
    val title : String,
    val description: String,
    val timeInfo : TimeInfo,
) {}

fun createDefaultTask(): Task {
    return Task(
        "Title of the Task",
        "Description of the Task",
        TimeInfo(
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis() + 1000000)
        )
    )
}

fun createDefaultTaskList() : ArrayList<Task>{
    val taskList : ArrayList<Task> = ArrayList<Task>()
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())
    taskList.add(createDefaultTask())

    return taskList
}