package com.fatec.petguide.ui

import android.Manifest
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fatec.petguide.R
import com.fatec.petguide.databinding.MainActivityBinding

class MainActivity : FragmentActivity() {

    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0
        )
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
        navigation = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }
}