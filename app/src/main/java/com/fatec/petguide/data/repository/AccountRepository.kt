package com.fatec.petguide.data.repository

import com.fatec.petguide.ui.states.UserState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountRepository {

    fun tryLogin(email: String, password: String, callback: OnAccountResponse) =
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) callback.successful() else callback.failure(UserState.FAILURE)
        }


    fun tryLogOff(): UserState {
        Firebase.auth.signOut()
        return isUserLogged()
    }

    fun isUserLogged(): UserState =
        if (Firebase.auth.currentUser != null) UserState.ACTIVATED
        else UserState.DISABLED



    interface OnAccountResponse {
        fun successful()
        fun failure(state: UserState)
    }
}