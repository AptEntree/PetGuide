package com.fatec.petguide.ui.pet

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentPetListBinding
import com.fatec.petguide.ui.base.BaseFragment


class PetListFragment : BaseFragment(), PetListAdapter.OnClickListener {

    private val viewModel: PetViewModel by viewModels()
    private var _binding: FragmentPetListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progress.visibility = View.VISIBLE
        viewModel.getPetList()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.createPetFragment)
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

        binding.header.searchBar.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) viewModel.getPetList()
            else viewModel.getPartialPetList(text)
        }

    }

    override fun setObservers() {
        viewModel.petListData.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = PetListAdapter(this, it)
            binding.progress.visibility = View.GONE
        }
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

    override fun onClick(id: String) {
        findNavController().navigate(
            R.id.petHistoricList,
            Bundle().also { it.putString("key", id) })
    }
}