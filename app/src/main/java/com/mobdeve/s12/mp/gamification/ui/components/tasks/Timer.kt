package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.ProfileViewModel
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TaskTimer(
    task : Task,
    onTick : (task : Task) -> Unit,
    profile: Profile,
    onProfileUpdate: (Profile) -> Unit
) {
    var isPlaying by remember { mutableStateOf(false)}
    var progress by remember { mutableFloatStateOf(task.getNormalizedProgress()) }
    val profileViewModel: ProfileViewModel = ProfileViewModel(context = LocalContext.current)

    val ONE_HOUR = 1000L * 60 * 60 * 1
    val TIME_PER_REWARD = ONE_HOUR

    LaunchedEffect(key1 = isPlaying) {
        while (isPlaying) {
            delay(1000L)
            task.updateProgress()
            progress = task.getNormalizedProgress()
            onTick(task)

        }
    }

    LaunchedEffect(key1 = isPlaying) {
        while (isPlaying) {
            delay(TIME_PER_REWARD) // Change this to control how much the user gains currency.
            MainScope().launch {
                profile.profileDetails.addCurrency(1)
                profileViewModel.updateCurrency(profile.profileDetails.currency)
                onProfileUpdate(profile)
            }
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