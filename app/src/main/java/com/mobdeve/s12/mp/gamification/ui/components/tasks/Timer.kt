package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import kotlinx.coroutines.delay

@Composable
fun TaskTimer(task : Task) {
    var isPlaying by remember { mutableStateOf(false)}
    var progress by remember { mutableFloatStateOf(task.getProgress()) }

    LaunchedEffect(key1 = isPlaying) {
        while (isPlaying) {
            delay(1000L)
            task.updateProgress()
            progress = task.getProgress()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .align(Alignment.Center),
            onClick = {
                isPlaying = !isPlaying
                if (isPlaying) {
                    task.play()
                } else {
                    task.pause()
                }
            },
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(64.dp)
            ) {
                // Draw the ring progress indicator
                Canvas(
                    modifier = Modifier.size(48.dp),
                    onDraw = {
                        drawArc(
                            color = AccentColor,
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(5.dp.toPx())
                        )
                    }
                )

                if (isPlaying) {
                    Icon(
                        painterResource(id = R.drawable.pause),
                        contentDescription = "Add FAB",
                        modifier = Modifier.size(20.dp, 20.dp),
                        tint = AccentColor
                    )
                } else {
                    Icon(
                        Icons.Sharp.PlayArrow,
                        contentDescription = "Add FAB",
                        modifier = Modifier.size(30.dp, 30.dp),
                        tint = AccentColor
                    )
                }
            }

        }
    }

}