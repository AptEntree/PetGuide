package com.fatec.petguide.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatec.petguide.R
import com.fatec.petguide.ui.base.BaseFragment

class CreatePetFragment : BaseFragment() {

    private val viewModel: PetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_create_pet, container, false)
}