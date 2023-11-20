package com.mobdeve.s12.mp.gamification.localdb

import android.content.Context
import kotlinx.coroutines.CoroutineScope

class RepositoryHolder {
    lateinit var database : AppDatabase
    lateinit var taskRepository: TaskRepository
    lateinit var skillRepository: SkillRepository
    lateinit var rewardRepository: RewardRepository
    lateinit var edgeRepository: EdgeRepository
    lateinit var cosmeticRepository: CosmeticRepository

    constructor(context: Context, scope : CoroutineScope){
        val db by lazy { AppDatabase.getInstance(context, scope) }
        val t by lazy { TaskRepository(database.taskDao()) }
        val s by lazy { SkillRepository(database.skillDao())}
        val r by lazy { RewardRepository(database.rewardDao())}
        val e by lazy { EdgeRepository(database.edgeDao())}
        val c by lazy { CosmeticRepository(database.cosmeticDao())}

        database = db
        taskRepository = t
        skillRepository = s
        rewardRepository = r
        edgeRepository = e
    }
}