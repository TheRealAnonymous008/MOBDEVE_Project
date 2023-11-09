package com.mobdeve.s12.mp.gamification.localdb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(task : TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}

// Repository
class TaskRepository(private val dao : TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        val entity = getTaskEntity(task)
        dao.insert(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(task: Task) {
        val entity = getTaskEntity(task)
        dao.update(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(task: Task) {
        val entity = getTaskEntity(task)
        dao.delete(entity)
    }
}

// View Model
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val allTasks: LiveData<List<TaskEntity>> = repository.allTasks.asLiveData()

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task : Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task : Task) = viewModelScope.launch {
        repository.delete(task)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
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

