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
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.CosmeticTypes
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// DB TabLE
@Entity(tableName = "cosmetics")
data class CosmeticEntity (
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name: String,
    val cost: Int,
    val image: String,
    val description: String,
    val cosmeticType: CosmeticTypes
)

// Queries
@Dao
interface CosmeticDao {
    @Query("SELECT * FROM cosmetics")
    fun getAll(): Flow<List<CosmeticEntity>>

    @Query("SELECT * FROM cosmetics WHERE id =(:id)")
    fun get(id: Long): CosmeticEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(cosmetic: CosmeticEntity) : Long

    @Update
    suspend fun update(cosmetic : CosmeticEntity)

    @Query("DELETE FROM cosmetics where id = (:id)")
    suspend fun delete(id : Long)

    @Query("DELETE FROM cosmetics")
    suspend fun deleteAll()
}

// Repository
class CosmeticRepository(private val dao : CosmeticDao) {
    val allCosmetics: Flow<List<CosmeticEntity>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(id: Long) : Cosmetic {
        val entity = dao.get(id)
        return getCosmeticFromEntity(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(cosmetic: Cosmetic) : Long {
        val entity = getCosmeticEntity(cosmetic)
        return dao.add(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(cosmetic: Cosmetic) {
        val entity = getCosmeticEntity(cosmetic)
        entity.id = cosmetic.id
        dao.update(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Long) {
        dao.delete(id)
    }
}

// View Model
class CosmeticViewModel(private val repository: CosmeticRepository) : ViewModel() {
    val allCosmetics: LiveData<List<CosmeticEntity>> = repository.allCosmetics.asLiveData()

    suspend fun get(id: Long): Result<Cosmetic> = suspendCoroutine { continuation ->
        viewModelScope.launch {
            try {
                val cosmetic = repository.get(id)
                continuation.resume(Result.success(cosmetic))
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    }

    fun insert(cosmetic : Cosmetic) = viewModelScope.launch {
        repository.add(cosmetic)
    }

    fun update(cosmetic : Cosmetic) = viewModelScope.launch {
        repository.update(cosmetic)
    }

    fun delete(id : Long) = viewModelScope.launch {
        repository.delete(id)
    }
}

class CosmeticViewModelFactory(private val repository: CosmeticRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CosmeticViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CosmeticViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Helper functions for converting to and from Entities

fun getCosmeticEntity(cosmetic : Cosmetic) : CosmeticEntity{
    return CosmeticEntity(
        name = cosmetic.name,
        description = cosmetic.description,
        image = cosmetic.image,
        cost = cosmetic.cost,
        cosmeticType = cosmetic.cosmeticTypes
    )
}

fun getCosmeticFromEntity(entry : CosmeticEntity) : Cosmetic {
    // TODO: Look into how to save images.
    if(entry.cosmeticType == CosmeticTypes.HEAD)
        return HeadCosmetic(
            id = entry.id,
            name = entry.name,
            description = entry.description,
            cost = entry.cost,
            image = entry.image
        )
    if(entry.cosmeticType == CosmeticTypes.TORSO)
        return TorsoCosmetic(
            id = entry.id,
            name = entry.name,
            description = entry.description,
            cost = entry.cost,
            image = entry.image
        )
    if(entry.cosmeticType == CosmeticTypes.LEGS)
        return LegsCosmetic(
            id = entry.id,
            name = entry.name,
            description = entry.description,
            cost = entry.cost,
            image = entry.image
        )
    if(entry.cosmeticType == CosmeticTypes.FEET)
        return FeetCosmetic(
            id = entry.id,
            name = entry.name,
            description = entry.description,
            cost = entry.cost,
            image = entry.image
        )
    throw error("No cosmetic type for cosmetic $entry")
}
