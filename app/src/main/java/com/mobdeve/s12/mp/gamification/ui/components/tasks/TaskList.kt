package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Task

@Composable
fun TaskList(taskList : ArrayList<Task>){
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)

        // TODO: Add padding as necessary

    ) {
        items(taskList) { task ->
            TaskEntry(task)
        }
    }
}
