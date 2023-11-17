package com.mobdeve.s12.mp.gamification.localdb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// DB TabLE
@Entity(tableName = "skills")
data class SkillEntity (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name: String,
    val description: String,
    val level: Int,
    val xp: Int,
    val priority: SkillPriority,
    val imageId: Int
)

// Queries
@Dao
interface SkillDao {
    @Query("SELECT * FROM skills")
    fun getAll(): Flow<List<SkillEntity>>

    @Query("SELECT * FROM skills WHERE id =(:id)")
    fun get(id: Long): SkillEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(skill: SkillEntity) : Long

    @Update
    suspend fun update(skill : SkillEntity)

    @Query("DELETE FROM skills where id = (:id)")
    suspend fun delete(id : Long)

    @Query("DELETE FROM skills")
    suspend fun deleteAll()
}

// Repository
class SkillRepository(private val dao : SkillDao) {
    val allSkills: Flow<List<SkillEntity>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(id: Long) : Skill {
        val entity = dao.get(id)
        return getSkillFromEntity(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(skill: Skill) : Long {
        val entity = getSkillEntity(skill)
        return dao.add(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(skill: Skill) {
        val entity = getSkillEntity(skill)
        dao.update(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Long) {
        dao.delete(id)
    }
}

// View Model
class SkillViewModel(private val repository: SkillRepository) : ViewModel() {
    val allSkills: LiveData<List<SkillEntity>> = repository.allSkills.asLiveData()

    suspend fun get(id: Long): Result<Skill> = suspendCoroutine { continuation ->
        viewModelScope.launch {
            try {
                val skill = repository.get(id)
                continuation.resume(Result.success(skill))
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    }

    fun insert(skill : Skill) = viewModelScope.launch {
        repository.add(skill)
    }

    fun update(skill : Skill) = viewModelScope.launch {
        repository.update(skill)
    }

    fun delete(id : Long) = viewModelScope.launch {
        repository.delete(id)
    }
}

class SkillViewModelFactory(private val repository: SkillRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkillViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SkillViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Helper functions for converting to and from Entities

fun getSkillEntity(skill : Skill) : SkillEntity{
    return SkillEntity(
        name = skill.name,
        description = skill.description,
        priority = skill.priority,
        level = skill.level,
        imageId = skill.imageId,
        xp = skill.xp
    )
}

fun getSkillFromEntity(entry : SkillEntity) : Skill{
    // TODO: Look into how to save images.
    return Skill(
        id = entry.id,
        xp = entry.xp,
        name = entry.name,
        level = entry.level,
        priority = entry.priority,
        description = entry.description
    )
}
