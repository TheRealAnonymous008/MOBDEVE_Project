package com.mobdeve.s12.mp.gamification.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mobdeve.s12.mp.gamification.model.Task
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.S)
fun notifyTask(context: Context, task : Task){
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, task.id.toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)


    val scheduledTime = task.timeInfo.datetimeFrom ?: return

    val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = scheduledTime.time
    }

    if (alarmManager.canScheduleExactAlarms()) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    } else {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}

fun unnotifyTask(context: Context, task : Task){
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, task.id.toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)
}
