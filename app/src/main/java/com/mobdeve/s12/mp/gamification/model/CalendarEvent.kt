package com.mobdeve.s12.mp.gamification.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.random.Random

data class Event(
    val name: String,
    val color: Color,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val description: String? = null,
)


fun getSampleEvents() : List<Event> {
    return listOf(
        Event(
            name = "Google I/O Keynote",
            color = Color(0xFFAFBBF2),
            start = LocalDateTime.parse("2021-05-18T09:00:00"),
            end = LocalDateTime.parse("2021-05-18T11:00:00"),
            description = "Tune in to find out about how we're furthering our mission to organize the world’s information and make it universally accessible and useful.",
        ),
        Event(
            name = "Developer Keynote",
            color = Color(0xFFAFBBF2),
            start = LocalDateTime.parse("2021-05-18T09:00:00"),
            end = LocalDateTime.parse("2021-05-18T10:00:00"),
            description = "Learn about the latest updates to our developer products and platforms from Google Developers.",
        ),
        Event(
            name = "What's new in Android",
            color = Color(0xFF1B998B),
            start = LocalDateTime.parse("2021-05-18T10:00:00"),
            end = LocalDateTime.parse("2021-05-18T11:00:00"),
            description = "In this Keynote, Chet Haase, Dan Sandler, and Romain Guy discuss the latest Android features and enhancements for developers.",
        ),
        Event(
            name = "What's new in Material Design",
            color = Color(0xFF6DD3CE),
            start = LocalDateTime.parse("2021-05-18T11:00:00"),
            end = LocalDateTime.parse("2021-05-18T11:45:00"),
            description = "Learn about the latest design improvements to help you build personal dynamic experiences with Material Design.",
        ),
        Event(
            name = "What's new in Machine Learning",
            color = Color(0xFFF4BFDB),
            start = LocalDateTime.parse("2021-05-18T10:00:00"),
            end = LocalDateTime.parse("2021-05-18T11:00:00"),
            description = "Learn about the latest and greatest in ML from Google. We’ll cover what’s available to developers when it comes to creating, understanding, and deploying models for a variety of different applications.",
        ),
        Event(
            name = "What's new in Machine Learning",
            color = Color(0xFFF4BFDB),
            start = LocalDateTime.parse("2021-05-18T10:30:00"),
            end = LocalDateTime.parse("2021-05-18T11:30:00"),
            description = "Learn about the latest and greatest in ML from Google. We’ll cover what’s available to developers when it comes to creating, understanding, and deploying models for a variety of different applications.",
        ),
        Event(
            name = "Jetpack Compose Basics",
            color = Color(0xFF1B998B),
            start = LocalDateTime.parse("2021-05-20T12:00:00"),
            end = LocalDateTime.parse("2021-05-20T13:00:00"),
            description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
        ),
    )
}


class EventsProvider : PreviewParameterProvider<Event> {
    override val values = getSampleEvents().asSequence()
}

// Wrapper class for Tasks and Events,
data class TaskEvent(
    val task: Task,
    val color: Color,
    val dateFrom : LocalDateTime,
    val dateTo: LocalDateTime
)

fun getTaskEvent(task: Task, color : Color?) : TaskEvent {
    var taskColor : Color? = color
    if(taskColor == null){
        // Assign a random color
        taskColor = randomColor()
    }

    var dateTimeFrom = task.timeInfo.datetimeFrom
    if (dateTimeFrom == null) {
        dateTimeFrom = task.timeInfo.datetimeCreated
    }

    var dateTimeTo = task.timeInfo.dateTimeTo
    if (dateTimeTo == null) {
        dateTimeTo = task.timeInfo.datetimeCreated
    }

    return TaskEvent(task, taskColor, getLocalDateTime(dateTimeFrom), getLocalDateTime(dateTimeTo))
}

fun randomColor(alpha:Int=255) = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = alpha
)

fun getLocalDateTime(t : Timestamp) : LocalDateTime{
    return t.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}