package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TaskListHolder
import com.mobdeve.s12.mp.gamification.model.createEmptyTask
import com.mobdeve.s12.mp.gamification.notifications.unnotifyTask
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun TaskList(taskList : TaskListHolder, profile : Profile, repo : RepositoryHolder){
    val scope = CoroutineScope(Dispatchers.Main)
    var taskListState = remember { mutableStateListOf(*taskList.tasks.toTypedArray())}
    val context = LocalContext.current

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {
            items(taskList.tasks) { task ->
                if (!task.isFinished)
                    TaskEntry(task, profile,
                    onUpdate = {
                        scope.launch(Dispatchers.IO) {
                            repo.taskRepository.update(it)
//                            notifyTask(context , it)
                        }
                    },
                    onDelete={
                        taskListState.remove(it)
                        scope.launch(Dispatchers.IO) {
                            repo.taskRepository.delete(it.id)
                            repo.rewardRepository.deleteWithTask(it.id)
                            unnotifyTask(context, it)
                        }
                    },
                        repo
                    )
            }

            item {
                Text(text = "Finished tasks")
            }

            items(taskList.getSorted()) { task ->
                if (task.isFinished) {
                    TaskEntry(task, profile,
                    onUpdate = {
                        scope.launch(Dispatchers.IO) {
                            repo.taskRepository.update(it)
                        }
                        unnotifyTask(context, task)
                    },
                    onDelete = {
                        taskListState.remove(it)
                        scope.launch(Dispatchers.IO) {
                            repo.taskRepository.update(it)
                            repo.rewardRepository.deleteWithTask(it.id)
                        }
                        it.unfinish()
                        unnotifyTask(context, task)
                    },
                        repo
                    )
                }
            }
        }


        var isShowingTaskDetails = remember { mutableStateOf(false)}
        var taskInserted = remember { mutableStateOf<Task?>(null)}

        TaskDialog(
            isVisible = isShowingTaskDetails,
            task = taskInserted.value,
            profile = profile,
            onUpdate = {
                scope.launch(Dispatchers.IO) {
                    val id = repo.taskRepository.insert(it)
                    it.id = id
                    taskList.add(it)
                    taskListState.add(it)

                    repo.rewardRepository.insertForTask(it)

//                    notifyTask(context, it)
                }
            },
            onDelete = {
                scope.launch(Dispatchers.IO) {
                    repo.taskRepository.update(it)
                    repo.rewardRepository.deleteWithTask(it.id)

                    unnotifyTask(context, it)
                }
                it.unfinish()
            },

            repo = repo
        )


        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart),
            onClick = {
                taskInserted.value = createEmptyTask()
                isShowingTaskDetails.value = true
            },
            containerColor = AccentColor,
            contentColor = Color.Black,
            shape = CircleShape
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add FAB",
                modifier = Modifier.size(80.dp, 80.dp)
            )
        }
    }
}
