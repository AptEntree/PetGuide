package com.fatec.petguide.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatec.petguide.data.entity.UserEntity
import com.fatec.petguide.databinding.FragmentRegisterBinding
import com.fatec.petguide.ui.base.BaseFragment

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
    }
}