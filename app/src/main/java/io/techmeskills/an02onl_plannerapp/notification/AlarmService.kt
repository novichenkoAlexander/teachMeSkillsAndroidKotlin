package io.techmeskills.an02onl_plannerapp.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.techmeskills.an02onl_plannerapp.repositories.NotificationRepository

class AlarmService(val notificationRepository: NotificationRepository) : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}