package com.fatec.petguide.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatec.petguide.R
import com.fatec.petguide.ui.base.BaseFragment

class RegisterFragment : BaseFragment() {

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_register, container, false)

    override fun setObservers() {
        TODO("Not yet implemented")
    }
}