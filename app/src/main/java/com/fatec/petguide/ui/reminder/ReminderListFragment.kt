package com.fatec.petguide.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentReminderListBinding
import com.fatec.petguide.ui.base.BaseFragment
import com.fatec.petguide.ui.pet.PetViewModel

class ReminderListFragment : BaseFragment() {

    private val viewModel: PetViewModel by viewModels()
    private var _binding: FragmentReminderListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.footer.menuHome.setOnClickListener {
            findNavController().navigate(R.id.calendarFragment)
        }

        binding.footer.menuEvents.setBackgroundResource(R.drawable.selected_menu_item_bg)

        binding.footer.menuPets.setOnClickListener {
            findNavController().navigate(R.id.petListFragment)
        }
    }
}