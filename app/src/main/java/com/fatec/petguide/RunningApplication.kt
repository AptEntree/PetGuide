package com.fatec.petguide

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class RunningApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            "running_channel",
            "running notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}