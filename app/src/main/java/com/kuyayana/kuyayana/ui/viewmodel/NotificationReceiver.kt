package com.kuyayana.kuyayana.ui.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kuyayana.kuyayana.ui.view.showNotification

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Event Reminder"
        val content = intent.getStringExtra("content") ?: "Event is starting soon!"

        showNotification(context, title, content)
    }
}