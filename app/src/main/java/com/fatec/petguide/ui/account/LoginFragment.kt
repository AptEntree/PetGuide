package com.fatec.petguide.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatec.petguide.R
import com.fatec.petguide.databinding.FragmentLoginBinding
import com.fatec.petguide.ui.base.BaseFragment
import com.fatec.petguide.util.states.UserState
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment() {

    private val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        with(binding) {
            loginEnterButton.setOnClickListener {
                if (loginEmailInputText.text.toString().isEmpty()) {
                    Snackbar.make(root, "Campo de email em branco.", Snackbar.LENGTH_SHORT).show()
                } else if (loginPasswordInputText.text.toString().isEmpty()) {
                    Snackbar.make(root, "Campo de senha em branco.", Snackbar.LENGTH_SHORT).show()
                } else {
                    viewModel.loginWithCredentials(
                        loginEmailInputText.text.toString(),
                        loginPasswordInputText.text.toString()
                    )
                }
            }

            registerTextButton.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
        }
        return binding.root
    }

    override fun setObservers() {
        viewModel.userState.observe(viewLifecycleOwner) {
            when(it) {
                UserState.ACTIVATED -> findNavController().navigate(R.id.calendarFragment)
                else -> showToast("Credenciais incorretas, tente novamente")
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun setMenu() {}

}