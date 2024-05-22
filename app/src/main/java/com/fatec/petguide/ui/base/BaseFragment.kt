package com.fatec.petguide.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fatec.petguide.ui.states.UserState

abstract class BaseFragment: Fragment() {

    private val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }
    open fun setObservers() {
        viewModel.userState.observe(viewLifecycleOwner) {
            Log.i("pedro", "aqui: $it")
            when (it) {
                UserState.ACTIVATED -> testShowToast(UserState.ACTIVATED.toString())
                UserState.REGISTERED -> testShowToast(UserState.REGISTERED.toString())
                UserState.NO_SUCH_PASSWORD -> testShowToast(UserState.NO_SUCH_PASSWORD.toString())
                UserState.NO_SUCH_EMAIL -> testShowToast(UserState.NO_SUCH_EMAIL.toString())
                UserState.FAILURE -> testShowToast(UserState.FAILURE.toString())
                else -> testShowToast("Erro")
            }
        }
    }
    open fun testShowToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }
}