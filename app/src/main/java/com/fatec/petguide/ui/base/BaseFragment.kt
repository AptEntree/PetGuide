package com.fatec.petguide.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fatec.petguide.util.states.UserState

abstract class BaseFragment: Fragment() {

    private val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        setMenu()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun setObservers() {
        viewModel.userState.observe(viewLifecycleOwner) {
            Log.i("pedro", "aqui: $it")
            when (it) {
                UserState.ACTIVATED -> showToast(UserState.ACTIVATED.toString())
                UserState.REGISTERED -> showToast(UserState.REGISTERED.toString())
                UserState.NO_SUCH_PASSWORD -> showToast(UserState.NO_SUCH_PASSWORD.toString())
                UserState.NO_SUCH_EMAIL -> showToast(UserState.NO_SUCH_EMAIL.toString())
                UserState.FAILURE -> showToast(UserState.FAILURE.toString())
                else -> showToast("Erro")
            }
        }
    }
    open fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }
    protected abstract fun setMenu()
}