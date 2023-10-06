package com.mobdeve.s12.mp.gamification.ui.components

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntry(task : Task){
    var show by remember {mutableStateOf(true) }
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )


    SwipeToDismiss(
        state = dismissState,
        background = {},
        dismissContent = {TaskEntryBox(task)})
}

@Composable 
fun TaskEntryBox(task: Task){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(75.dp),
    ){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor)
        ) {
            Row {
                Spacer(Modifier.width(10.dp))
                Box(modifier = Modifier
                    .background(AccentColor)
                    .width(5.dp)
                    .fillMaxHeight()) {

                }
                Spacer(Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(start = 10.dp),
                ) {
                    // Task name
                    Text(
                        text = task.title,
                        color = TextColor,
                        modifier = Modifier.padding(top=10.dp)
                    )

                    // Task description
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = task.description,
                        color = TextColor
                    )

                    // Task duration
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = task.timeInfo.getDurationAsString(),
                        color = TextColor
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                ) {
                    Button(
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                            .align(Alignment.Center),
                        onClick = { /*  TODO: Add button click logic */ }
                    ){

                    }
                }
            }
        }
    }
}