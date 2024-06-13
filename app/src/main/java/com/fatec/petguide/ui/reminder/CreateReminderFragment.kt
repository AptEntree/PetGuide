package com.fatec.petguide.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentCreateReminderBinding
import com.fatec.petguide.ui.base.BaseFragment

class CreateReminderFragment : BaseFragment() {

    private val viewModel: ReminderViewModel by viewModels()

    private var _binding: FragmentCreateReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            with(header) {
                title.text = "Editar lembrete"
                icCheck.setOnClickListener { tryCreateReminder() }
                icClose.setOnClickListener { tryClose() }
            }
//            reminderCategorySpinner.adapter = ArrayAdapter
        }


        setMenu()

    }

    override fun setMenu() {
        with(binding.footer) {
            menuHome.setOnClickListener {
                findNavController().navigate(R.id.calendarFragment)
            }

            menuEvents.setBackgroundResource(R.drawable.selected_menu_item_bg)

            menuPets.setOnClickListener {
                findNavController().navigate(R.id.petListFragment)
            }
        }

    }

    private fun tryCreateReminder() {
        TODO()
    }

    private fun tryClose() {
        TODO()
    }
}