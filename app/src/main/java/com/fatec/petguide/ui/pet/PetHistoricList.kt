package com.fatec.petguide.ui.pet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.HistoricEntity
import com.fatec.petguide.databinding.FragmentPetHistoricListBinding
import com.fatec.petguide.ui.base.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

class PetHistoricList : BaseFragment(), View.OnClickListener {

    private val viewModel: PetViewModel by viewModels()
    private var _binding: FragmentPetHistoricListBinding? = null
    private val binding get() = _binding!!
    private lateinit var petId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetHistoricListBinding.inflate(inflater, container, false)
        petId = arguments?.getString("key")?: ""

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPetHistoricList(petId)

        binding.fab.setOnClickListener {
            showFileChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.tryCreatePetHistoric(
                petId,
                HistoricEntity(
                    historicId = null,
                    date = Calendar.getInstance().time.toString(),
                    file = data.data?.path.toString(),
                    title = File(data.data?.path.toString()).name
                )
            )
        }

        viewModel.getPetHistoricList(petId)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(intent, "select a file"), 100)
    }

    override fun setObservers() {
        viewModel.petHistoricListData.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = HistoricPetAdapter(this, it)
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

    override fun onClick(p0: View?) {
    }
}