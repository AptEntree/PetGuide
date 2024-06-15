package com.fatec.petguide.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            findNavController().navigate(R.id.calendarFragment)
        }

        binding.footer.menuEvents.setOnClickListener {
            findNavController().navigate(R.id.reminderListFragment)
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
                        race = null
                    )
                )
            }
            header.icClose.setOnClickListener {
                findNavController().navigate(R.id.petListFragment)
            }
        }
    }
}