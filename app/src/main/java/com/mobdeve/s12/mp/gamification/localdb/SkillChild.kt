package com.mobdeve.s12.mp.gamification.localdb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobdeve.s12.mp.gamification.model.Skill
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// DB TabLE
@Entity(tableName = "edges", primaryKeys = ["parent", "child"])
data class SkillEdge (
    @ColumnInfo(name = "parent") val parent: Long,
    @ColumnInfo(name = "child") val child: Long,
)

// Queries
@Dao
interface SkillEdgeDao {
    @Query("SELECT * FROM edges")
    fun getAll(): Flow<List<SkillEdge>>

    @Query("SELECT * FROM edges WHERE parent =(:id)")
    fun getChildren(id: Long): Flow<List<SkillEdge>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(edge: SkillEdge)

    @Query("DELETE FROM edges WHERE parent = (:parent) AND child = (:child)")
    suspend fun delete(parent: Long, child : Long)

    @Query("DELETE FROM edges WHERE parent = (:id) OR child = (:id)")
    suspend fun deleteSkill(id : Long)

    @Query("DELETE FROM edges")
    suspend fun deleteAll()
}

// Repository
class EdgeRepository(private val dao : SkillEdgeDao) {
    val allEdges: Flow<List<SkillEdge>> = dao.getAll()

    fun getChildren(skill : Skill) : Flow<List<SkillEdge>> {
        return dao.getChildren(skill.id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(parent: Skill, child: Skill) {
        val edge = SkillEdge(parent.id, child.id)
        dao.add(edge)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(parent: Skill, child : Skill) {
        dao.delete(parent.id, child.id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteSkill(skill : Skill) {
        dao.deleteSkill(skill.id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        dao.deleteAll()
    }
}

// View Model
class EdgeViewModel(private val repository: EdgeRepository) : ViewModel() {
    val allEdges: LiveData<List<SkillEdge>> = repository.allEdges.asLiveData()

    fun getChildren(skill: Skill): LiveData<List<SkillEdge>> {
        return repository.getChildren(skill).asLiveData()
    }

    fun add(parent: Skill, child : Skill) = viewModelScope.launch {
        repository.add(parent, child)
    }

    fun delete(parent : Skill, child : Skill) = viewModelScope.launch {
        repository.delete(parent, child)
    }

    fun deleteSkill(skill : Skill) = viewModelScope.launch {
        repository.deleteSkill(skill)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class EdgeViewModelFactory(private val repository: EdgeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EdgeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EdgeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}