package com.mobdeve.s12.mp.gamification.localdb

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import java.sql.Timestamp

// DB TabLE
@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val timeCreated : Long,
    val timeFrom : Long?,
    val timeTo : Long?
)

// Queries
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<TaskEntity>

    @Insert
    fun insertAll(vararg task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)
}

// Helper functions for converting to and from Entities

fun getTaskEntity(task : Task) : TaskEntity{
    var timeFromTimestamp = task.timeInfo.datetimeFrom
    var timeFrom : Long? = null
    if (timeFromTimestamp !== null) {
        timeFrom = timeFromTimestamp.time
    }

    var timeToTimestamp = task.timeInfo.dateTimeTo
    var timeTo : Long? = null
    if (timeToTimestamp !== null) {
        timeTo = timeToTimestamp.time
    }


    return TaskEntity(
        title = task.title,
        description = task.description,
        timeCreated = task.timeInfo.datetimeCreated.time,
        timeFrom = timeFrom,
        timeTo = timeTo
    )
}

fun getTaskFromEntity(entry : TaskEntity) : Task{
    val dateTimeCreated = Timestamp(entry.timeCreated)
    var dateTimeFrom = dateTimeCreated
    if (entry.timeFrom !== null) {
        val dateTimeFrom = Timestamp(entry.timeFrom)
    }
    var dateTimeTo = dateTimeCreated
    if (entry.timeFrom !== null) {
        val dateTimeTo = Timestamp(entry.timeFrom)
    }

    return Task (
        id = entry.id,
        title = entry.title,
        description = entry.description,
        timeInfo = TimeInfo(
            datetimeCreated =  dateTimeCreated,
            datetimeFrom = dateTimeFrom,
            dateTimeTo = dateTimeTo
        )
    )
}
