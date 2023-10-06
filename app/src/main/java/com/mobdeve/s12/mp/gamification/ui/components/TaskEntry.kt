package com.mobdeve.s12.mp.gamification.ui.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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


@Composable
fun TaskEntry(task : Task){
    var offset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false)}
    var isReleased by remember { mutableStateOf(false) }

    val springSpec = spring<Float>(stiffness = Spring.StiffnessLow)

    // Animation parameters
    val targetOffset = if (isDragging) offset else 0f

    // Animate the offset using a spring animation when isDragging changes
    val animatedOffset by animateFloatAsState(
        targetValue = targetOffset,
        animationSpec = if (isReleased) springSpec else SpringSpec(1f, stiffness = Spring.StiffnessVeryLow),
        label = ""
    )

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
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, _, _ ->
                        val horizontalOffset = pan.x

                        // Only allow dragging from the left (horizontalOffset should be positive)
                        if (horizontalOffset >= 0) {
                            offset += horizontalOffset

                            // Restrict the offset within a range (0 to 300)
                            if (offset < 0) {
                                offset = 0f
                            } else if (offset > 300) {
                                offset = 300f
                            }

                            isDragging = true
                        }
                    }
                }
                .offset { IntOffset(x = animatedOffset.roundToInt(), y = 0) }
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

    DisposableEffect(isReleased) {
        if (!isDragging && isReleased) {
            isReleased = false
            isDragging = false
            offset = 0f
        }

        onDispose { }
    }

    LaunchedEffect(isDragging) {
        if (!isDragging) {
            isReleased = true
        }
    }
}