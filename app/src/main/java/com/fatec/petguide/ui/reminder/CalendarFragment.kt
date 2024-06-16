package com.fatec.petguide.ui.reminder

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentCalendarBinding
import com.fatec.petguide.ui.base.BaseFragment
import java.util.Calendar
import java.util.Date

class CalendarFragment : BaseFragment() {

    private val viewModel: ReminderViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        viewModel.getReminderListForToday()

        return binding.root
    }

    override fun setMenu() {
        binding.footer.menuHome.setBackgroundResource(R.drawable.selected_menu_item_bg)

        binding.footer.menuEvents.setOnClickListener {
            findNavController().navigate(R.id.reminderListFragment)
        }
        binding.footer.menuPets.setOnClickListener {
            findNavController().navigate(R.id.petListFragment)
        }
        binding.exit.setOnClickListener {
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

        binding.welcome.text =
            "Bem vindo(a): $userName\n Hoje é ${Calendar.getInstance().time.date} de ${
                getMonth(Calendar.getInstance().time.month)
            }"

        viewModel.reminderListForTodayData.observe(viewLifecycleOwner) {
            binding.mainText.text =
                if (it.isEmpty()) "Você ainda não tem nenhum evento agendado para hoje"
                else "Você tem ${it.size} lembretes marcados para hoje"
        }
    }

    private fun getMonth(value: Int) =
        when (value) {
            0 -> "Janeiro"
            1 -> "Fevereiro"
            2 -> "Março"
            3 -> "Abril"
            4 -> "Maio"
            5 -> "Junho"
            6 -> "Julho"
            7 -> "Agosto"
            8 -> "setembro"
            9 -> "outubro"
            10 -> "novembro"
            11 -> "dezembro"
            else -> ""
        }

}