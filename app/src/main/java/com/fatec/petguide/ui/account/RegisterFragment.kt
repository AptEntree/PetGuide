package com.fatec.petguide.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.UserEntity
import com.fatec.petguide.databinding.FragmentRegisterBinding
import com.fatec.petguide.ui.base.BaseFragment
import com.fatec.petguide.util.states.UserState
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : BaseFragment() {

    private val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        binding.registerButton.setOnClickListener {
            tryRegister()
        }

        return binding.root
    }

    private fun tryRegister() {
        if (doCheck()) {
            if (binding.privacyButton.isChecked) {
                viewModel.registerNewUser(
                    UserEntity(
                        id = null,
                        name = binding.nameInput.text.toString(),
                        cpf = binding.documentInput.text.toString(),
                        email = binding.emailInput.text.toString(),
                        phone = binding.phoneInput.text.toString(),
                        password = binding.passwordInput.text.toString()
                    )
                )
            } else {
                Snackbar.make(binding.root, "Aceite os termos de uso para completar o registro", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(binding.root, "Algum campo não foi preenchido corretamente", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun setObservers() {
        viewModel.userState.observe(viewLifecycleOwner) {
            when(it) {
                UserState.REGISTERED -> findNavController().navigate(R.id.loginFragment)
                else -> showToast("Um erro aconteceu, verifique os campos e tente novamente")
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun doCheck(): Boolean {
        with(binding) {
            if (nameInput.text.toString().isEmpty()) return false
            if (documentInput.text.toString().isEmpty()) return false
            if (emailInput.text.toString().isEmpty()) return false
            if (phoneInput.text.toString().isEmpty()) return false
            if (passwordInput.text.toString().isEmpty()) return false
        }
        return true
    }

    override fun setMenu() {}
}