package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.createDefaultTask
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.mobdeve.s12.mp.gamification.ui.theme.body1

@Composable
fun TaskDetailsLayout(
    task : Task,
    profile : Profile,
    onDelete : () -> Unit,
    repo: RepositoryHolder) {

    var title by remember { mutableStateOf(task.title)}
    var description by remember { mutableStateOf(task.description) }

    Column(
        modifier = Modifier
            .background(SecondaryColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = title,
            onValueChange = {
                task.title = it
                title = it
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (title.isBlank()) {
                        Text(
                            text = "Untitled Task",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        BasicTextField(
            value = description,
            onValueChange = {
                description= it
                task.description = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextColor,
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (description.isBlank()) {
                        Text(
                            text = "Blank Description",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scheduled
        Text(
            modifier = Modifier
                .background(OtherAccent)
                .padding(start = 10.dp, end = 10.dp),
            text = "Scheduled",
            fontSize = 18.sp,
            color = TextColor,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TimeInfoDetails(timeInfo = task.timeInfo)

        // Edit TimeInfo Button
        Button(
            onClick = {
                // Set showDialog to true to trigger the dialog
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Edit Time Info", fontWeight = FontWeight.Bold)
        }

        // Rewards list
        Text(
            text = "Rewards",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
        ) {
            TaskRewardsList(task = task, profile = profile, repo)
        }

        // Delete Button
        Button(
            onClick = {
                onDelete()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Delete Task", fontWeight = FontWeight.Bold)
        }

        // Use Dialog to display the TimeInfoEditDialog when showDialog is true
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text("Edit Time Info")
                },
                text = {
                    TimeInfoEditDialog(
                        initialTimeInfo = task.timeInfo,
                        onTimeInfoUpdated = { updatedTimeInfo ->
                            task.timeInfo = updatedTimeInfo
                            repo.taskRepository.update(task)
                        },
                        onDismiss = {
                            showDialog = false
                        }
                    )
                },
                confirmButton = {
                    // You can add confirm button if needed
                },
                dismissButton = {
                    // You can add dismiss button if needed
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInfoEditDialog(
    initialTimeInfo: TimeInfo,
    onTimeInfoUpdated: (TimeInfo) -> Unit,
    onDismiss: () -> Unit
) {
    var updatedTimeInfo by remember { mutableStateOf(initialTimeInfo) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Edit Time Info")
        },
        text = {
            // Use the CustomTimePicker composable when the user clicks on "Edit Time"
            if (showTimePicker) {
                CustomTimePicker(
                    selectedTime = updatedTimeInfo.datetimeFrom ?: Date(),
                    onTimeSelected = {
                        updatedTimeInfo = updatedTimeInfo.copy(datetimeFrom = it)
                        showTimePicker = false
                    }
                )
            } else {
                // Display selected time
                Text(
                    text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(updatedTimeInfo.datetimeFrom ?: Date()),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    style = body1
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Call the callback with the updated TimeInfo
                    onTimeInfoUpdated(updatedTimeInfo)
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTimePicker(
    selectedTime: Date,
    onTimeSelected: (Date) -> Unit
) {
    var time by remember { mutableStateOf(selectedTime) }
    var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Display selected time
        Text(
            text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(time),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            style = MaterialTheme.typography.body1
        )

        // Custom time picker dialog
        if (showTimePicker) {
            CustomTimePickerDialog(
                initialTime = time,
                onTimeSelected = {
                    time = it
                    onTimeSelected(it)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTimePickerDialog(
    initialTime: Date,
    onTimeSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = {
            keyboardController?.hide()
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Hour input
                OutlinedTextField(
                    value = hour.toString(),
                    onValueChange = {
                        if (it.isNotBlank()) {
                            hour = it.toInt()
                        }
                    },
                    label = { Text("Hour") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                // Minute input
                OutlinedTextField(
                    value = minute.toString(),
                    onValueChange = {
                        if (it.isNotBlank()) {
                            minute = it.toInt()
                        }
                    },
                    label = { Text("Minute") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm button
            Button(
                onClick = {
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                    selectedTime.set(Calendar.MINUTE, minute)
                    onTimeSelected(selectedTime.time)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Confirm")
            }
        }
    }
}


