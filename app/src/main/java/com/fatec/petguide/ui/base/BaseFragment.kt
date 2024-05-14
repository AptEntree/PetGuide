package com.fatec.petguide.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }
    abstract fun setObservers()
}