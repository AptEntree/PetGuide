package com.fatec.petguide.data.repository

import android.util.Log
import com.fatec.petguide.data.entity.UserEntity
import com.fatec.petguide.data.util.Constants
import com.fatec.petguide.ui.states.UserState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AccountRepository {

    fun tryLogin(email: String, password: String, callback: OnAccountResponse) =
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) callback.successful(UserState.ACTIVATED) else callback.failure(UserState.FAILURE)
        }

    fun tryLogOff(): UserState {
        Firebase.auth.signOut()
        return isUserLogged()
    }

    fun isUserLogged(): UserState =
        if (Firebase.auth.currentUser != null) UserState.ACTIVATED
        else UserState.DISABLED

    fun registerUser(userEntity: UserEntity, callback: OnAccountResponse) {
        with(Firebase.auth) {
            createUserWithEmailAndPassword(userEntity.email, userEntity.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Pedro", "createUserWithEmailAndPassword:success")
                        createUserOnDatabase(this.uid!!, userEntity)
                        callback.successful(UserState.REGISTERED)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Pedro", "createUserWithEmailAndPassword:failure", task.exception)
                        callback.failure(UserState.FAILURE)
                    }
                }
        }
    }

    fun getCurrentUserId() = Firebase.auth.currentUser?.uid

    private fun createUserOnDatabase(uid: String, userEntity: UserEntity) {
        with(
            FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(uid)
        ) {
            child(Constants.USER_NAME).setValue(userEntity.name)
            child(Constants.USER_CPF).setValue(userEntity.cpf)
            child(Constants.USER_EMAIL).setValue(userEntity.email)
            child(Constants.USER_PHONE).setValue(userEntity.phone)
            child(Constants.USER_PASSWORD).setValue(userEntity.password)
        }
    }

    interface OnAccountResponse {
        fun successful(state: UserState)
        fun failure(state: UserState)
    }
}