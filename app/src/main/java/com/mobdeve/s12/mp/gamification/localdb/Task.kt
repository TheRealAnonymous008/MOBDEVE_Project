package com.mobdeve.s12.mp.gamification.localdb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
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
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val title: String,
    val description: String,
    val timeCreated : Long,
    val isFinished : Boolean,
    val datetimeFrom: Long?,
    val datetimeTo: Long?,
    val dateTimeFinished : Long? ,
    val progress: Long,
)

// Queries
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(task: TaskEntity) : Long

    @Update
    suspend fun update(task : TaskEntity)

    @Query("DELETE FROM tasks where id = (:id)")
    suspend fun delete(id : Long)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Long): TaskEntity?
}

// Repository
class TaskRepository(private val dao : TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) : Long{
        val entity = getTaskEntity(task)
        val taskId = dao.add(entity)

        return taskId
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(task: Task) {
        val entity = getTaskEntity(task)
        entity.id = task.id

        dao.update(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Long) {
        dao.delete(id)
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

    fun delete(id : Long) = viewModelScope.launch {
        repository.delete(id)
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
    var timeFrom : Long? = getLongOrNullFromTimestamp(task.timeInfo.datetimeFrom)
    var timeTo : Long? = getLongOrNullFromTimestamp(task.timeInfo.dateTimeTo)
    var timeFinished : Long? = getLongOrNullFromTimestamp(task.timeInfo.dateTimeFinished)


    return TaskEntity(
        title = task.title,
        description = task.description,
        timeCreated = task.timeInfo.datetimeCreated.time,
        isFinished = task.isFinished,
        datetimeFrom = timeFrom,
        datetimeTo = timeTo,
        dateTimeFinished = timeFinished,
        progress = task.timeInfo.progress
    )
}

fun getTaskFromEntity(entry : TaskEntity) : Task{
    val dateTimeCreated = Timestamp(entry.timeCreated)
    var dateTimeFrom: Timestamp? = getTimestampOrNull(entry.datetimeFrom)
    var dateTimeTo: Timestamp? = getTimestampOrNull(entry.datetimeTo)
    var dateTimeFinished: Timestamp? = getTimestampOrNull(entry.dateTimeFinished)


    return Task (
        id = entry.id,
        title = entry.title,
        description = entry.description,
        timeInfo = TimeInfo(
            datetimeCreated =  dateTimeCreated,
            datetimeFrom = dateTimeFrom,
            dateTimeTo = dateTimeTo,
            dateTimeFinished = dateTimeFinished,
            progress = entry.progress,
        ),
        isFinished = entry.isFinished
    )
}


fun getTimestampOrNull(x : Long?) : Timestamp?{
    if (x!== null) {
        return Timestamp(x)
    }
    return null
}

fun getLongOrNullFromTimestamp(x : Timestamp?) : Long?{
    if (x !== null) {
        return x.time
    }
    return null
}