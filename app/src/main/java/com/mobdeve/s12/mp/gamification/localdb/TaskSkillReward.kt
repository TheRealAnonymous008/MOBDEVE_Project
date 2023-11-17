package com.mobdeve.s12.mp.gamification.localdb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Task
import kotlinx.coroutines.flow.Flow

// DB TabLE
@Entity(tableName = "task_skills", primaryKeys = ["taskId", "skillId"])
data class RewardEntity (
    @ColumnInfo(name = "taskId") val taskId: Long, // Foreign key referencing the task
    @ColumnInfo(name = "skillId") val skillId: Long, // Foreign key referencing the skill
    val reward: Float
)

// Queries
@Dao
interface RewardDao {

    @Query("SELECT * FROM task_skills")
    fun getAll(): Flow<List<RewardEntity>>

    @Query("SELECT * FROM task_skills where taskId = (:id)")
    fun getForTask(id : Long): List<RewardEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(reward: RewardEntity) : Long

    @Update
    suspend fun update(reward: RewardEntity)

    @Query("DELETE FROM task_skills where  taskId = (:id)")
    suspend fun deleteWithTask(id : Long)

    @Query("DELETE FROM task_skills where  skillId = (:id)")
    suspend fun deleteWithSkill(id : Long)

    @Query("DELETE FROM task_skills")
    suspend fun deleteAll()
}

class RewardRepository(private val dao : RewardDao) {
    val allRewards: Flow<List<RewardEntity>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getWithTask(task: Task ): Flow<List<RewardEntity>> {
        return getWithTask(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(reward: Reward ): Long {
        val entity = getTaskSkillReward(reward)
        return dao.add(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(reward: Reward) {
        val entity = getTaskSkillReward(reward)
        dao.update(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWithTask(id: Long) {
        dao.deleteWithTask(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWithSkill(id: Long) {
        dao.deleteWithSkill(id)
    }
}

// ViewModel
class RewardViewModel(private val repository: RewardRepository) : ViewModel() {

    val allRewards: LiveData<List<RewardEntity>> = repository.allRewards.asLiveData()

    suspend fun getRewardWithTask(task: Task): LiveData<List<RewardEntity>> {
        return repository.getWithTask(task).asLiveData()
    }

    suspend fun insert(reward: Reward): Long {
        return repository.insert(reward)
    }

    suspend fun update(reward: Reward) {
        repository.update(reward)
    }

    suspend fun deleteWithTask(id: Long) {
        repository.deleteWithTask(id)
    }

    suspend fun deleteWithSkill(id: Long) {
        repository.deleteWithSkill(id)
    }
}

// ViewModel Factory
class RewardViewModelFactory(private val repository: RewardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RewardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Helper functions for converting to and from Entities

fun getTaskSkillRewards(task: Task) : List<RewardEntity>{
    var rewards : ArrayList<RewardEntity> = ArrayList<RewardEntity>()

    task.rewards.forEach {
        rewards.add(
            RewardEntity(
                taskId = task.id,
                skillId = it.skill.id,
                reward = it.xp
            )
        )
    }

    return rewards
}

fun getTaskSkillReward(reward : Reward) : RewardEntity {
    return RewardEntity(
        reward.task.id,
        reward.skill.id,
        reward.xp
    )
}