package com.fatec.petguide.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.util.states.UserState

open class BaseViewModel : ViewModel() {

    protected val accountRepository = AccountRepository()

    protected var _userState: MutableLiveData<UserState> = MutableLiveData<UserState>()
    val userState: MutableLiveData<UserState> get() = _userState

    fun isUserLogged() {
        _userState.postValue(accountRepository.isUserLogged())
    }

    fun logOffUser() {
        _userState.postValue(accountRepository.tryLogOff())
    }


}