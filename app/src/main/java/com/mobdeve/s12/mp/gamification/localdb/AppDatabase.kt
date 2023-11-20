package com.mobdeve.s12.mp.gamification.localdb

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.capitalize
import androidx.core.content.res.ResourcesCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobdeve.s12.mp.gamification.BuildConfig
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.CosmeticTypes
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
import com.mobdeve.s12.mp.gamification.model.createDefaultTaskList
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.io.path.fileVisitor
import kotlin.math.cos

@Database(entities=[TaskEntity::class, SkillEntity::class, RewardEntity::class, SkillEdge::class, CosmeticEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun skillDao() : SkillDao
    abstract fun rewardDao() : RewardDao
    abstract fun edgeDao() : SkillEdgeDao
    abstract fun cosmeticDao() : CosmeticDao

    private class AppDBCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val skills = populateSkills(database.skillDao())
                    val tasks = populateTasks(database.taskDao(), skills)
                    val cosmetics = populateCosmetics(database.cosmeticDao(), context)
                    val rewards = populateRewards(database.rewardDao(), tasks)
                    populateEdges(database.edgeDao(), skills)
                }
            }
        }

        suspend fun populateTasks(dao : TaskDao, skills : ArrayList<Skill>) : ArrayList<Task>{
            // Delete all content here.
            dao.deleteAll()
            val dummy = createDefaultTaskList(skills)

            dummy.forEach {
                val t = getTaskEntity(it)
                dao.add(t)
            }
            return dummy
        }

        suspend fun populateSkills(dao : SkillDao) : ArrayList<Skill>{
            // Delete all content here.
            dao.deleteAll()
            val dummy = createDefaultSkillList()
            dummy.forEach {
                val t = getSkillEntity(it)
                dao.add(t)
            }
            return dummy
        }

        suspend fun populateRewards(dao : RewardDao, tasks : ArrayList<Task>) : ArrayList<Reward>{
            // Delete all content here.
            val rewards = ArrayList<Reward>()
            dao.deleteAll()
            tasks.forEach {task ->
                task.getRewards().forEach {
                    dao.add(getTaskSkillReward(it))
                    rewards.add(it)
                }
            }
            return rewards
        }

        suspend fun populateEdges(dao : SkillEdgeDao, skills: ArrayList<Skill>) {
            dao.deleteAll()
            skills.forEach {skill ->
                skill.children.forEach {child ->
                    dao.add(SkillEdge(skill.id, child.id))
                }
            }
        }

        suspend fun populateCosmetics(dao: CosmeticDao, context: Context) {
            dao.deleteAll()
            val cosmeticList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()

            cosmeticList.add(
                HeadCosmetic(
                    id = 0,
                    name = "Default Head",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_default_head),
                    description = "The default head"
                )
            )
            cosmeticList.add(
                TorsoCosmetic(
                    id = 0,
                    name = "Default Torso",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_default_torso),
                    description = "The default torso"
                )
            )
            cosmeticList.add(
                LegsCosmetic(
                    id = 0,
                    name = "Default Legs",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_default_legs),
                    description = "The default legs"
                )
            )
            cosmeticList.add(
                FeetCosmetic(
                    id = 0,
                    name = "Default Feet",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_default_feet),
                    description = "The default feet"
                )
            )
            cosmeticList.add(
                HeadCosmetic(
                    id = 0,
                    name = "Stark's Hair",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_stark_head),
                    description = "Short red hair with a dark contrasting black in the center."
                )
            )
            cosmeticList.add(
                TorsoCosmetic(
                    id = 0,
                    name = "Stark's Coat",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_stark_torso),
                    description = "Stark's coat"
                )
            )
            cosmeticList.add(
                LegsCosmetic(
                    id = 0,
                    name = "Stark's Pants",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_stark_legs),
                    description = "Stark's pants"
                )
            )
            cosmeticList.add(
                FeetCosmetic(
                    id = 0,
                    name = "Stark's Shoes",
                    cost = 0,
                    image = context.resources.getResourceName(R.drawable.cosmetic_stark_feet),
                    description = "Stark's shoes"
                )
            )



            cosmeticList.forEach {
                val t = getCosmeticEntity(it)
                dao.add(t)
            }


                // i had a sol that just took all the drawable names, then I realized its useless, kept this here incase we need it tho
//            R.drawable::class.java.fields.mapNotNull { field ->
//                ResourcesCompat.getDrawable(Resources.getSystem(), field.getInt(null), null)
//                    ?.let {
//                        if(field.name.startsWith("cosmetic")) {
//                            val result = field.name
//                            if(field.name.endsWith("head"))
//                                heads.add(result)
//                            if(field.name.endsWith("torso"))
//                                torso.add(result)
//                            if(field.name.endsWith("legs"))
//                                legs.add(result)
//                            if(field.name.endsWith("feet"))
//                                feet.add(result)
//                        }
//                    }
//            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDBCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
