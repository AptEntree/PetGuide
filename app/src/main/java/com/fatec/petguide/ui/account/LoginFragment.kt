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
import com.fatec.petguide.ui.states.UserState

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

        binding.loginEnterButton.setOnClickListener {
//            viewModel.loginWithCredentials(
//                "pedro.henriquevieira@outlook.com",
//                "test123"
//            )
            viewModel.loginWithCredentials(
                binding.loginEmailInputText.text.toString(),
                binding.loginPasswordInputText.text.toString()
            )
        }

        binding.registerTextButton.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        return binding.root
    }

    override fun setObservers() {
        viewModel.userState.observe(viewLifecycleOwner) {
            when(it) {
                UserState.ACTIVATED -> findNavController().navigate(R.id.calendarFragment)
                else -> showToast("Um erro aconteceu, tente novamente mais tarde")
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}