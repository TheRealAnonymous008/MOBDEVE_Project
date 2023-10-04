package com.mobdeve.s12.mp.gamification.model

import java.sql.Timestamp

data class TimeInfo(
    val datetimeCreated: Timestamp,
    val datetimeFrom: Timestamp,
    val dateTimeTo: Timestamp,
){}

data class Task(
    val title : String,
    val description: String,
    val timeInfo : TimeInfo
) {}

fun createDefaultTask(): Task {
    return Task(
        "Title of the Task",
        "Description of the Task",
        TimeInfo(
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis())
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