package com.fatec.petguide.ui.account

import com.fatec.petguide.data.entity.UserEntity
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.ui.base.BaseViewModel
import com.fatec.petguide.util.states.UserState

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

    fun registerNewUser(userEntity: UserEntity) {
        accountRepository.registerUser(userEntity, this)
    }

    override fun successful(state: UserState) {
        _userState.postValue(state)
    }

    override fun failure(state: UserState) {
        _userState.postValue(state)
    }

}