package com.fatec.petguide.ui.reminder

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.databinding.FragmentCreateReminderBinding
import com.fatec.petguide.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

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

        viewModel.getPetNameList()

        with(binding) {
            with(header) {
                title.text = "Criar lembrete"
                icCheck.setOnClickListener { tryCreateReminder() }
                icClose.setOnClickListener { tryClose() }
                dateSelection.setOnClickListener {
                    showDatePicker()
                }
            }
        }

        setMenu()

    }

    override fun setObservers() {
        super.setObservers()
        viewModel.petListData.observe(viewLifecycleOwner) {
            binding.petSpinner.adapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_modified, it).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }
    }

    override fun setMenu() {
        with(binding.footer) {
            menuHome.setOnClickListener {
                createDialog(R.id.calendarFragment)
            }

            menuEvents.setBackgroundResource(R.drawable.selected_menu_item_bg)

            menuPets.setOnClickListener {
                createDialog(R.id.petListFragment)
            }
        }

    }

    private fun showDatePicker() {
        with(Calendar.getInstance()) {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    binding.dateText.text = "Data: $formattedDate"
                },
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    private fun tryCreateReminder() {
        if (verifyFields()) {
            viewModel.tryCreateReminder(ReminderEntity(
                reminderId = null,
                title = binding.reminderTitleInputText.text.toString(),
                date = binding.dateText.text.toString().replace("Data: ", ""),
                petId = binding.petSpinner.selectedItem.toString(),
                color = null,
                category = null,
                location = binding.reminderLocationInputText.text.toString()
            ))
            Toast.makeText(context, "Pet adicionado com sucesso", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.reminderListFragment)
        } else {
            Snackbar.make(
                binding.root,
                "Algum campo está em branco. Por favor verique",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun tryClose() {
        createDialog(R.id.reminderListFragment)
    }

    private fun createDialog(id: Int) {
        AlertDialog.Builder(context)
            .setTitle("Dados serão excluidos")
            .setMessage("Realmente deseja cancelar a criação de um lembrete?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Confirmar") { _, _ ->
                findNavController().navigate(id)
            }.show()
    }

    private fun verifyFields(): Boolean {
        with(binding) {
            if (reminderTitleInputText.text.toString().isEmpty()) return false
            if (reminderLocationInputText.text.toString().isEmpty()) return false
            if (dateText.text.toString() == "Data:") return false
            return true
        }
    }
}