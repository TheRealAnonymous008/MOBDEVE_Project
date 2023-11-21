package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.sql.Date
import java.sql.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeInput(timeState : MutableState<Timestamp??>, onUpdate: (t : Timestamp?) -> Unit) {

    val state = rememberDatePickerState()
    val date = remember { mutableStateOf<Date?>(null) }

    if (timeState.value != null) {
        date.value = Date(timeState.value!!.time)
    }

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val selectedDateMillis = state.selectedDateMillis
                        if (selectedDateMillis != null) {
                            val selectedDate = Date(selectedDateMillis)
                            val timestamp = Timestamp(selectedDateMillis)
                            timeState.value = timestamp
                            onUpdate(timestamp)
                            date.value = selectedDate
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = state,
            )
        }
    }

    // Just display the date
    Text(
        text = "${date.value}",
        modifier = Modifier
            .clickable {
                openDialog.value = true
            }
    )
}
