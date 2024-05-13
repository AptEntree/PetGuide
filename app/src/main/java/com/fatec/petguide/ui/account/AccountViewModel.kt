package com.fatec.petguide.ui.account

import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.ui.base.BaseViewModel
import com.fatec.petguide.ui.states.UserState

class AccountViewModel : BaseViewModel(), AccountRepository.OnAccountResponse {

    fun loginWithCredentials(
        email: String,
        password: String
    ) {
        accountRepository.tryLogin(
            email = email,
            password = password,
            callback = this
        )
    }

    override fun successful() {
        _userState.postValue(UserState.ACTIVATED)
    }

    override fun failure(state: UserState) {
        _userState.postValue(state)
    }

}