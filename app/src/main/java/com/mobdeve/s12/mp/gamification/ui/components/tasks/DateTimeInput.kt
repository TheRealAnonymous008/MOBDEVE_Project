package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.model.getTimeStampDate
import java.sql.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeInput(timeStamp : Timestamp?, onUpdate: (t : Timestamp?) -> Unit) {

    val state = rememberDatePickerState()
    var timeStampState by remember {mutableStateOf(timeStamp)}
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
                            timeStampState = Timestamp(selectedDateMillis)
                            onUpdate(timeStampState)
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


    Text(
        text = "${formatTimestampDate(timeStampState)}",
        modifier = Modifier
            .clickable {
                openDialog.value = true
            }
    )
}
