package com.fatec.petguide.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentCalendarBinding
import com.fatec.petguide.ui.base.BaseFragment

class CalendarFragment() : BaseFragment() {

    private val viewModel: CalendarViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

}