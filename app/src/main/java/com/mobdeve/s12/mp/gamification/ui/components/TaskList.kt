package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskList(taskList : ArrayList<Task>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        // TODO: Add padding as necessary

    ) {
        items(taskList) { task ->
            TaskEntry(task)
        }
    }
}

@Composable
fun TaskEntry(task : Task){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom=10.dp)
    ){
        Column(
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth()
                .padding(20.dp)
                .padding(start = 40.dp)
        ) {
            Text(
                text = task.title,
                color = TextColor
            )

            Text(
                text = task.description,
                color = TextColor
            )
        }
    }
}