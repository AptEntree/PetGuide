package com.fatec.petguide.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.data.util.Constants
import com.fatec.petguide.util.states.UserState
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue

open class BaseViewModel : ViewModel() {

    protected val accountRepository = AccountRepository()

    protected var _userState: MutableLiveData<UserState> = MutableLiveData<UserState>()
    val userState: LiveData<UserState> get() = _userState

    private var _userName: MutableLiveData<String> = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    fun isUserLogged() {
        _userState.postValue(accountRepository.isUserLogged())
    }

    fun logOffUser() {
        _userState.postValue(accountRepository.tryLogOff())
    }

    fun getUserName() {
        accountRepository.getUserName(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (snapshot.key == accountRepository.getCurrentUserId()) {
                        _userName.postValue(snapshot.child(Constants.USER_NAME).getValue<String>())
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }


}