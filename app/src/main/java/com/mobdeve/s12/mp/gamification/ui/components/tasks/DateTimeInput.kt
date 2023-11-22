package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.model.formatTimestampTime
import com.mobdeve.s12.mp.gamification.model.getCurrentTimeStamp
import java.sql.Timestamp
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeInput(timeStamp : Timestamp?, onUpdate: (t : Timestamp?) -> Unit) {

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var timeStampState by remember {mutableStateOf(timeStamp)}
    val openDateDialog = remember { mutableStateOf(false) }
    val openTimeDialog = remember { mutableStateOf(false) }

    if (timeStamp != null){
        datePickerState.setSelection(timeStamp.time)
    }

    Row() {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (openDateDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDateDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDateDialog.value = false

                                if (timeStampState == null){
                                    timeStampState = getCurrentTimeStamp()
                                }

                                if (datePickerState.selectedDateMillis != null) {
                                    val selectedDate = Date(datePickerState.selectedDateMillis!!)
                                    val localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

                                    val calendar = Calendar.getInstance().apply {
                                        time = timeStampState
                                        set(Calendar.YEAR, localDate.year)
                                        set(Calendar.MONTH, localDate.monthValue - 1) // -1 is because monthValue is 1 indexed but MONTH isn't
                                        set(Calendar.DAY_OF_MONTH, localDate.dayOfMonth)
                                    }
                                    timeStampState = Timestamp(calendar.time.time)
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
                                openDateDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                    )
                }
            }


            Text(
                text = "${formatTimestampDate(timeStampState)}",
                modifier = Modifier
                    .clickable {
                        openDateDialog.value = true
                    }
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (openTimeDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openTimeDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openTimeDialog.value = false

                                if (timeStampState == null){
                                    timeStampState = getCurrentTimeStamp()
                                }

                                val calendar = Calendar.getInstance().apply {
                                    time = timeStampState!!
                                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    set(Calendar.MINUTE, timePickerState.minute)
                                }
                                timeStampState = Timestamp(calendar.time.time)
                                onUpdate(timeStampState)
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openTimeDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                ) {
                    TimePicker(
                        state = timePickerState,
                    )
                }
            }

            Text(
                text = "${formatTimestampTime(timeStampState)}",
                modifier = Modifier
                    .clickable {
                        openTimeDialog.value = true
                    }
            )
        }
    }
}
