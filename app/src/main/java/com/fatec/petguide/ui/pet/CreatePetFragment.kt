package com.fatec.petguide.ui.pet

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.PetEntity
import com.fatec.petguide.databinding.FragmentCreatePetBinding
import com.fatec.petguide.ui.base.BaseFragment


class CreatePetFragment : BaseFragment() {

    private val viewModel: PetViewModel by viewModels()
    private var _binding: FragmentCreatePetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setMenu() {
        binding.footer.menuHome.setOnClickListener {
            createDialog(R.id.calendarFragment)
        }

        binding.header.title.text = "Adicionar pet"

        binding.footer.menuEvents.setOnClickListener {
            createDialog(R.id.reminderListFragment)
        }
        binding.footer.menuPets.setBackgroundResource(R.drawable.selected_menu_item_bg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            header.icCheck.setOnClickListener {
                viewModel.tryCreatePet(
                    PetEntity(
                        petId = null,
                        name = petNameInputText.text.toString(),
                        age = petAgeInputText.text.toString(),
                        coat = petCoatInputText.text.toString(),
                        race = raceSpinner.selectedItem.toString()
                    )
                )
                Toast.makeText(context, "Pet adicionado com sucesso", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.petListFragment)
            }
            header.icClose.setOnClickListener {
                createDialog(R.id.petListFragment)
            }

            context?.let {
                ArrayAdapter.createFromResource(
                    it,
                    R.array.pet_array,R.layout.spinner_modified
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.raceSpinner.adapter = adapter
                }
            }
        }
    }

    private fun createDialog(id: Int) {
        AlertDialog.Builder(context)
            .setTitle("Dados serão excluidos")
            .setMessage("Realmente deseja cancelar a adição de um novo pet?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Confirmar") { _, _ ->
                findNavController().navigate(id)
            }.show()
    }
}