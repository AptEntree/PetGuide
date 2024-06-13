package com.fatec.petguide.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fatec.petguide.R
import com.fatec.petguide.databinding.MainActivityBinding
import com.fatec.petguide.receiver.NotificationReceiver
import java.util.Calendar


class MainActivity : FragmentActivity() {

    private lateinit var navigation: NavController

    @SuppressLint("ScheduleExactAlarm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0
        )

        setContentView(MainActivityBinding.inflate(layoutInflater).root)
        navigation =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController

        startAlarmManager()
    }

    private fun startAlarmManager() {
        applicationContext.getSystemService(AlarmManager::class.java).setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().time.time,
            86400000,
            PendingIntent.getBroadcast(
                applicationContext,
                0,
                Intent(applicationContext, NotificationReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}