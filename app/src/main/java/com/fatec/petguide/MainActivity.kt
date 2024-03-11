package com.fatec.petguide

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.fatec.petguide.data.repository.AccountRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}