package com.fatec.petguide.ui.pet

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore.AUTHORITY
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.HistoricEntity
import com.fatec.petguide.databinding.FragmentPetHistoricListBinding
import com.fatec.petguide.ui.base.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

class PetHistoricList : BaseFragment(), HistoricPetAdapter.OnClickListener {

    private val viewModel: PetViewModel by viewModels()
    private var _binding: FragmentPetHistoricListBinding? = null
    private val binding get() = _binding!!
    private lateinit var petId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetHistoricListBinding.inflate(inflater, container, false)
        petId = arguments?.getString("key") ?: ""

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPetHistoricList(petId)

        binding.fab.setOnClickListener {
            showFileChooser()
        }

        binding.header.searchBar.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) viewModel.getPetHistoricList(petId)
            else viewModel.getPetPartialHistoricList(petId, text)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.tryCreatePetHistoric(
                petId,
                HistoricEntity(
                    historicId = null,
                    date = "${Calendar.getInstance().time.date} de ${getMonth(Calendar.getInstance().time.month)}",
                    file = data.data?.toString(),
                    title = File(data.data?.path.toString()).name
                )
            )
        }

        viewModel.getPetHistoricList(petId)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(intent, "select a file"), 100)
    }

    override fun setObservers() {
        viewModel.petHistoricListData.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = HistoricPetAdapter(this, it)
            binding.emptyText.visibility = View.GONE
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

    override fun onClick(file: String?) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            Log.i("pedro", "${Uri.parse(file)}")
            data = Uri.parse(file)
        }

        startActivity(intent)
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