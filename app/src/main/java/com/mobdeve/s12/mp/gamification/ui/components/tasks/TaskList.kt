package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TaskListHolder
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import androidx.compose.runtime.getValue
import com.mobdeve.s12.mp.gamification.model.createDefaultTask
import com.mobdeve.s12.mp.gamification.model.createEmptyTask


@Composable
fun TaskList(taskList : TaskListHolder, profile : Profile){
    var taskListState = remember { mutableStateListOf<Task>() }

    for(t : Task in taskList.tasks){
        taskListState.add(t)
    }

    Box() {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)

        ) {
            items(taskListState) { task ->
                if (!task.isFinished)
                    TaskEntry(task, profile)
            }

            item {
                Text(text = "Finished tasks")
            }

            items(taskListState) { task ->
                if (task.isFinished)
                    TaskEntry(task, profile)
            }
        }

        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart),
            onClick = {
                val t : Task = createEmptyTask()
                taskList.add(t)
                taskListState.add(t)
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
