package com.example.motounplugged.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.motounplugged.R
import com.example.motounplugged.database.datastore.FocusDataStore
import com.example.motounplugged.utils.NotificationsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FocusModeService : Service() {

    private val serviceScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var focusDataStore: FocusDataStore
    private var focusJob: Job? = null
    private var focusEndTime: Long = 0L


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        focusDataStore = FocusDataStore(applicationContext)
    }

    private fun createNotificationChannel() {
        Log.d("FOCUS_DEBUG", "startForegroundNotification()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationsUtils.CHANNEL_ID,
                "Modo Foco",
                NotificationManager.IMPORTANCE_LOW
            )
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("FOCUS_DEBUG", "Service iniciado. action=${intent?.action}")
        when (intent?.action) {
            FocusActions.ACTION_START -> {

                startFocus(
                    profileId = intent.getLongExtra("profileId", -1L),
                    durationMinutes = intent.getIntExtra("durationMinutes", 0),
                    interruptions = intent.getBooleanExtra("interruptions", true),
                    passwordRequired = intent.getBooleanExtra("passwordRequired", false)
                )
            }
            FocusActions.ACTION_STOP -> {
                Log.d("FOCUS_DEBUG", "STOP recebido")
                stopFocus()
            }
        }

        return START_NOT_STICKY
    }

    private fun startFocus(
        profileId: Long,
        durationMinutes: Int,
        interruptions: Boolean,
        passwordRequired: Boolean
    ) {
        createNotificationChannel()
        startForegroundNotification()
        // evita múltiplos focos
        focusJob?.cancel()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (nm.isNotificationPolicyAccessGranted && interruptions) {
            nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        }

        val focusEndTime =
            System.currentTimeMillis() + durationMinutes * 60_000L

        focusJob = serviceScope.launch {
            focusDataStore.setFocusActive(
                active = true,
                profileId = profileId,
                endTime = focusEndTime,
                passwordRequired = passwordRequired
            )

            while (true) {
                val remaining = focusEndTime - System.currentTimeMillis()

                if (remaining <= 0) {
                    stopFocus()
                    break
                }

                updateNotification(remaining)
                kotlinx.coroutines.delay(1_000L) // atualiza a cada segundo
            }
        }
    }

    private fun stopFocus() {
        focusJob?.cancel()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (nm.isNotificationPolicyAccessGranted) {
            nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }

        serviceScope.launch {
            focusDataStore.clearFocus()
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun startForegroundNotification() {
        val notification = NotificationsUtils.createFocusNotification(this)
        startForeground(
            NotificationsUtils.NOTIFICATION_ID,
            notification
        )
    }

    override fun onDestroy() {
        focusJob?.cancel()
        super.onDestroy()
    }

    private fun formatRemainingTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateNotification(remainingMs: Long) {
        val notification = NotificationCompat.Builder(
            this,
            NotificationsUtils.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Modo foco ativo")
            .setContentText("Tempo restante: ${formatRemainingTime(remainingMs)}")
            .setOngoing(true)
            .build()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NotificationsUtils.NOTIFICATION_ID, notification)
    }
}




