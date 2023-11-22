package com.mobdeve.s12.mp.gamification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobdeve.s12.mp.gamification.components.LockScreenOrientation
import com.mobdeve.s12.mp.gamification.localdb.CosmeticViewModel
import com.mobdeve.s12.mp.gamification.localdb.CosmeticViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.EdgeViewModel
import com.mobdeve.s12.mp.gamification.localdb.EdgeViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.localdb.RewardViewModel
import com.mobdeve.s12.mp.gamification.localdb.RewardViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModel
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModel
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.getCosmeticFromEntity
import com.mobdeve.s12.mp.gamification.localdb.getSkillFromEntity
import com.mobdeve.s12.mp.gamification.localdb.getTaskFromEntity
import com.mobdeve.s12.mp.gamification.model.CosmeticHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.ProfileViewModel
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.model.TaskListHolder
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.notifications.AlarmReceiver
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow
import com.mobdeve.s12.mp.gamification.ui.components.avatar.AvatarEditWindow
import com.mobdeve.s12.mp.gamification.ui.components.skilltree.SkillTreeWindow


class MainActivity : AppCompatActivity() {
    companion object Routes {
        val MAIN_WINDOW = "main_window"
        val AVATAR_WINDOW = "avatar_window"
        val SKILLTREE_WINDOW = "skilltree_window"
    }

    private val REQUEST_CODE = 1
    private lateinit var repositoryHolder : RepositoryHolder

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as MainApplication).repositoryHolder.taskRepository)
    }

    private val skillViewModel : SkillViewModel by viewModels {
        SkillViewModelFactory((application as MainApplication).repositoryHolder.skillRepository)
    }

    private val rewardViewModel : RewardViewModel by viewModels {
        RewardViewModelFactory((application as MainApplication).repositoryHolder.rewardRepository)
    }

    private val edgeViewModel : EdgeViewModel by viewModels {
        EdgeViewModelFactory((application as MainApplication).repositoryHolder.edgeRepository)
    }

    private val cosmeticViewModel: CosmeticViewModel by viewModels {
        CosmeticViewModelFactory((application as MainApplication).repositoryHolder.cosmeticRepository)
    }

    lateinit var profileSharedPref : ProfileViewModel
    lateinit var profileState : MutableState<Profile>
    lateinit var cosmeticsHolder : CosmeticHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        repositoryHolder = ((application as MainApplication)).repositoryHolder
        profileSharedPref = ProfileViewModel(application as MainApplication)
        profileSharedPref.updateCurrency(999)
        profileState = mutableStateOf(generateDefaultProfile(application as MainApplication))
        cosmeticsHolder = CosmeticHolder()

        fetchTasks().observe(this) { tasks ->
            fetchSkills().observe(this) { skills ->
                fetchRewards(tasks, skills)
                fetchEdges(skills)
            }
        }
        fetchCosmetics()

        update()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Channel"
            val descriptionText = "Channel for Task notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(AlarmReceiver.NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun fetchTasks(): LiveData<TaskListHolder> {
        val tasksLiveData = MutableLiveData<TaskListHolder>()

        taskViewModel.allTasks.observe(this) { tasks ->
            val profile = profileState.value
            profile.tasks.clear()

            tasks?.let {
                for (task in it) {
                    profile.tasks.add(getTaskFromEntity(task))
                }
                profileState.value = profile
                tasksLiveData.value = profile.tasks
                update()
            }
        }
        return tasksLiveData
    }


    private fun fetchSkills(): LiveData<SkillListHolder> {
        val skillsLiveData = MutableLiveData<SkillListHolder>()
        skillViewModel.allSkills.observe(this) { skills ->
            val profile = profileState.value
            profile.skills.clear()

            skills?.let {
                for (skill in it) {
                    profile.skills.add(getSkillFromEntity(skill))
                }
                profileState.value = profile
                skillsLiveData.value = profile.skills
                update()

            }
        }
        return skillsLiveData
    }

    private fun fetchRewards(holder: TaskListHolder, skills: SkillListHolder) {
        val profile = profileState.value
        val tasks = holder.tasks

        for (task in tasks) {
            rewardViewModel.getRewardWithTask(task).observe(this) {
                it?.let {rl ->
                    task.clearRewards()
                    for (r in rl) {
                        val sk  = skills.find(r.skillId)
                        if (sk !== null){
                            val reward : Reward = Reward(task, sk, r.reward)
                            task.addReward(reward)
                        } else {
                            Log.e("X", r.skillId.toString())
                        }
                    }

                    holder.tasks = tasks
                    profile.tasks = holder
                    profileState.value = profile
                    update()
                }
            }
        }

    }

    private fun fetchEdges(holder : SkillListHolder) {
        val profile = profileState.value
        val skills = holder.skills

        for (skill in skills) {
            edgeViewModel.getChildren(skill).observe(this) {
                it?.let {sl ->
                    skill.children.clear()
                    for (s in sl) {
                        val sk  = holder.find(s.child)
                        if (sk !== null){
                            skill.addChild(sk)
                        } else {
                            Log.e("X", s.child.toString())
                        }
                    }

                    holder.skills = skills
                    profile.skills = holder
                    profileState.value = profile
                    update()
                }
            }
        }
    }

    private fun fetchCosmetics() {
        cosmeticViewModel.allCosmetics.observe(this) { cosmetics ->
            val profile = profileState.value
            profile.cosmetics.clear()
            cosmeticsHolder.clear()

            for (cosmeticEntity in cosmetics) {
                val cosmetic = getCosmeticFromEntity(cosmeticEntity)
                if(cosmetic.owned){
                    profile.cosmetics.add(cosmetic)
                    Log.d("cosmetic", "${cosmetic.name}")
                }

                cosmeticsHolder.add(cosmetic)
            }
            profileState.value = profile
            update()

        }
    }

    private fun update() {
        setContent {
            navigation()
        }
    }

    @Composable
    fun navigation() {
        val navController = rememberNavController()
        LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED)
        NavHost(navController, startDestination = Routes.MAIN_WINDOW) {
            composable(Routes.MAIN_WINDOW) {
                MainWindow(profile = profileState.value, cosmeticsHolder.cosmetics, repositoryHolder , navController = navController)
            }
            composable(Routes.AVATAR_WINDOW) {
                AvatarEditWindow(profileState.value.cosmetics.cosmetics, profileState.value.profileDetails.avatar, navController)
            }
            composable(Routes.SKILLTREE_WINDOW) {
                SkillTreeWindow(skillList = profileState.value.skills, profile = profileState.value)
            }
        }
    }
}





/*@Composable
fun navigatePage() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page",
        builder = {
            composable("login_page", content = { LoginPage(navController = navController) })
            composable("register_page", content = { Register(navController = navController) })
        }
    )
}*/