package com.mobdeve.s12.mp.gamification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.localdb.EdgeViewModel
import com.mobdeve.s12.mp.gamification.localdb.EdgeViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.localdb.RewardViewModel
import com.mobdeve.s12.mp.gamification.localdb.RewardViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModel
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModel
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.getSkillFromEntity
import com.mobdeve.s12.mp.gamification.localdb.getTaskFromEntity
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TaskListHolder
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow
import com.mobdeve.s12.mp.gamification.ui.components.avatar.AvatarEditWindow




class MainActivity : AppCompatActivity() {
    companion object Routes {
        val MAIN_WINDOW = "main_window"
        val AVATAR_WINDOW = "avatar_window"
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

    val profileState = mutableStateOf(generateDefaultProfile())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryHolder = ((application as MainApplication)).repositoryHolder

        fetchTasks().observe(this) { tasks ->
            fetchSkills().observe(this) { skills ->
                fetchRewards(tasks, skills)
                fetchEdges(skills)
            }
        }

        update()
    }

    private fun fetchTasks(): LiveData<TaskListHolder> {
        val tasksLiveData = MutableLiveData<TaskListHolder>()

        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let {
                val profile = profileState.value
                profile.tasks.clear()

                for (task in it) {
                    val task = getTaskFromEntity(task)
                    profile.tasks.add(task)
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

    private fun update() {
        setContent {
            navigation()
        }
    }

    @Composable
    fun navigation() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = Routes.MAIN_WINDOW) {
            composable(Routes.MAIN_WINDOW) {
                MainWindow(profile = profileState.value, repositoryHolder , navController = navController)
            }
            composable(Routes.AVATAR_WINDOW) {
                AvatarEditWindow(profileState.value.cosmetics, profileState.value.profileDetails.avatar, navController)
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