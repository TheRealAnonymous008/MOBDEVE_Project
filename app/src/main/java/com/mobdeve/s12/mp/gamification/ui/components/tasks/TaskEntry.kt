package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntry(task : Task, profile : Profile, onUpdate : (t : Task) -> Unit, onDelete : (t : Task) -> Unit, repo : RepositoryHolder) {

    var offsetX by remember { mutableStateOf(Offset.Zero) }
    var show by remember { mutableStateOf(true) }
    var dismissState by remember { mutableStateOf(false) }

    var isShowingTaskDetails = remember { mutableStateOf(false)}
    TaskDialog(
        isVisible = isShowingTaskDetails,
        task = task,
        profile = profile,
        onUpdate = onUpdate,
        onDelete = onDelete,
        repo = repo
    )

    if (show) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(75.dp)
                .offset(offsetX.x.dp, 0.dp),

            onClick = {
                isShowingTaskDetails.value = true
            }
        ) {
            // Show the task details as needed
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryColor),
            ) {
                Row {
                    Spacer(Modifier.width(10.dp))
                    Box(modifier = Modifier
                        .background(AccentColor)
                        .width(5.dp)
                        .fillMaxHeight()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragCancel = {
                                    offsetX = Offset.Zero
                                },
                                onDragEnd = {
                                    if (offsetX.x < 150) {
                                        offsetX = Offset.Zero
                                    } else {
                                        dismissState = true
                                        if (task.isFinished) {
                                            task.unfinish()
                                        } else {
                                            task.finish()
                                        }
                                        onUpdate(task)
                                        show = false
                                        offsetX = Offset(300f, 0f)
                                    }
                                }
                            ) { change, dragAmount ->
                                if (dragAmount.x > 0)
                                    offsetX += Offset(dragAmount.x, 0f) * 0.5f
                                change.consume()
                            }
                        }) {

                    }
                    Spacer(Modifier.width(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically
                    ) {

                        // Task duration
                        Box {
                            Text(
                                text = task.timeInfo.getTimeLeftAsString(),
                                color = TextColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .size(50.dp)
                                    .wrapContentHeight(align = CenterVertically),
                            )
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        var title = task.title
                        if (title.isBlank())
                            title = "Untitled Task"
                        Text(
                            text = title,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .width(150.dp)
                                .height(30.dp),
                            color = TextColor,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    TaskTimer(task = task)
                }
            }
        }
    }
}