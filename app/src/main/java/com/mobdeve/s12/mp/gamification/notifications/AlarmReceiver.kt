package com.mobdeve.s12.mp.gamification.notifications

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mobdeve.s12.mp.gamification.MainActivity

class AlarmReceiver() : BroadcastReceiver(){

    companion object {
        val NOTIFICATION_CHANNEL_ID = "notification_channel"
        val REQUEST_CODE = 100
    }
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = NOTIFICATION_CHANNEL_ID
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText("You have a task scheduled")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Released", "Released")
            // TODO: We need to get permissions before we can send notifications

//            // Request permissions
//            ActivityCompat.requestPermissions(
//                context as Activity,
//                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                REQUEST_CODE
//            )
            return
        }
        notificationManager.notify(0, builder.build())
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, proceed with your action
            } else {
                // Permission was denied, notify the user
//                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}