package com.example.motounplugged.utils

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.motounplugged.R

object NotificationsUtils {

    const val CHANNEL_ID = "focus_mode_channel"
    const val NOTIFICATION_ID = 1

    fun createFocusNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_focus_notification) //  correto
            .setContentTitle("Modo foco ativo")
            .setContentText("O foco está em execução")
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}

