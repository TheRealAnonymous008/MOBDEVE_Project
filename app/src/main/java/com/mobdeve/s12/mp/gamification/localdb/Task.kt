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
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val title: String,
    val description: String,
    val timeCreated : Timestamp,
    val timeFrom : Timestamp?,
    val timeTo : Timestamp?
)

// Queries
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<TaskEntity>

    @Insert
    fun insertAll(vararg task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)
}

// Helper functions for converting to and from Entities

fun getTaskEntity(task : Task) : TaskEntity{
    return TaskEntity(
        title = task.title,
        description = task.description,
        timeCreated = task.timeInfo.datetimeCreated,
        timeFrom = task.timeInfo.datetimeFrom,
        timeTo = task.timeInfo.dateTimeTo
    )
}

fun getTaskFromEntity(entry : TaskEntity) : Task{
    return Task (
        id = entry.uid,
        title = entry.title,
        description = entry.description,
        timeInfo = TimeInfo(
            datetimeCreated = entry.timeCreated,
            datetimeFrom = entry.timeFrom,
            dateTimeTo = entry.timeTo
        )
    )
}