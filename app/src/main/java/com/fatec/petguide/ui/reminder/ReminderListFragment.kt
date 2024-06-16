package com.fatec.petguide.ui.reminder

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentReminderListBinding
import com.fatec.petguide.ui.base.BaseFragment

class ReminderListFragment : BaseFragment() {

    private val viewModel: ReminderViewModel by viewModels()
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

        viewModel.getReminderList()
    }

    override fun setObservers() {
        viewModel.reminderListData.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ReminderAdapter(
                { TODO("Not yet implemented") }, it
            )
        }

        binding.header.searchBar.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) viewModel.getReminderList()
            else viewModel.getPartialReminderList(text)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.createReminderFragment)
        }

        binding.header.exit.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Saindo da aplicação")
                .setMessage("Você está saindo da aplicação, tem certeza disso?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Confirmar") { _, _ ->
                    viewModel.logOffUser()
                    findNavController().navigate(R.id.loginFragment)
                }.show()
        }
    }

    override fun setMenu() {
        binding.footer.menuHome.setOnClickListener {
            findNavController().navigate(R.id.calendarFragment)
        }

        binding.footer.menuEvents.setBackgroundResource(R.drawable.selected_menu_item_bg)

        binding.footer.menuPets.setOnClickListener {
            findNavController().navigate(R.id.petListFragment)
        }
    }
}