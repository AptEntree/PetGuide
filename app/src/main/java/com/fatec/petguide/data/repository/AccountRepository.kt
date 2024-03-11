package com.fatec.petguide.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountRepository {

    fun tryLogin(email: String, password: String, callback: onAccountResponse) =
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) callback.successful() else callback.failure()
        }


    fun tryLogOff(): Boolean {
        Firebase.auth.signOut()
        return isUserLogged()
    }

    fun isUserLogged(): Boolean =
        Firebase.auth.currentUser != null

    interface onAccountResponse {
        fun successful()
        fun failure()
    }
}