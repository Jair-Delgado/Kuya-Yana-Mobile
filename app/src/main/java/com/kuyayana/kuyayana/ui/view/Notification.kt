package com.kuyayana.kuyayana.ui.view

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.ui.viewmodel.NotificationReceiver
import android.provider.Settings
import java.util.Calendar

fun canScheduleExactAlarms(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        alarmManager.canScheduleExactAlarms()
    } else {
        true
    }
}

fun scheduleNotification(context: Context, title: String, content: String, eventStartTime: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("content", content)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Configura el alarm para 10 minutos antes del evento
    val triggerTimeMillis = eventStartTime - 10 * 60 * 1000 // 10 minutos antes

    if (canScheduleExactAlarms(context)) {
        try {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Toast.makeText(context, "No se puede programar alarmas exactas: ${e.message}", Toast.LENGTH_LONG).show()
        }
    } else {
        // Redirige al usuario a la configuración para otorgar el permiso
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        context.startActivity(intent)
    }
}

fun showNotification(context: Context, title: String, content: String) {
    val channelId = "event_notification_channel"
    val notificationId = 1

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Event Notification"
        val descriptionText = "Notifications for calendar events"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.event)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notify(notificationId, builder.build())
        } else {
            // Maneja el caso donde el permiso no está concedido
            Toast.makeText(context, "Permission to post notifications is not granted", Toast.LENGTH_SHORT).show()
        }
    }
}