package com.fatec.petguide

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.services.NotificationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0
        )

        Intent(applicationContext, NotificationService::class.java).also {
            it.action = NotificationService.Action.START.toString()
            startService(it)
        }

        AccountRepository().tryLogin(
            "pedro.henriquevieira@outlook.com",
            "pedrotest",
            callback = object : AccountRepository.onAccountResponse {
                override fun successful() {
                    Log.i("pedro", "true")
                }

                override fun failure() {
                    Log.i("pedro", "false")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(applicationContext, NotificationService::class.java).also {
            it.action = NotificationService.Action.STOP.toString()
            startService(it)
        }
    }
}