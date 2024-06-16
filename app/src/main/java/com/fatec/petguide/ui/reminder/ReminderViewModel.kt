package com.fatec.petguide.ui.reminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.data.repository.ReminderRepository
import com.fatec.petguide.data.util.Constants
import com.fatec.petguide.ui.base.BaseViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue
import java.util.Calendar

class ReminderViewModel : BaseViewModel() {

    private val reminderRepository = ReminderRepository(accountRepository.getCurrentUserId())

    private var _reminderListData: MutableLiveData<List<ReminderEntity>> = MutableLiveData()
    val reminderListData: LiveData<List<ReminderEntity>> get() = _reminderListData

    fun startValues() {
        reminderRepository.createReminder(
            ReminderEntity(
                title = "test_title",
                reminderId = null,
                category = "test_category",
                color = 0,
                date = 0L,
                location = "test_location",
                petId = "test_pet_id"
            )
        )
    }

    fun getReminderList() {
        reminderRepository.getReminderList(object : ChildEventListener {
            val list: MutableList<ReminderEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    list.add(createReminderEntity(snapshot))
                }
                Log.i("pedro", "list: $list")
                _reminderListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getReminderListForToday() {
        reminderRepository.getReminderList(object : ChildEventListener {
            val list: MutableList<ReminderEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    list.add(createReminderEntity(snapshot))
                }
                Log.i("pedro", "list: $list")
                _reminderListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun createReminderEntity(snapshot: DataSnapshot): ReminderEntity {
        return with(snapshot) {
            ReminderEntity(
                reminderId = key,
                title = child(Constants.REMINDER_TITLE).getValue<String>(),
                petId = child(Constants.REMINDER_PET_ID).getValue<String>(),
                category = child(Constants.REMINDER_CATEGORY).getValue<String>(),
                location = child(Constants.REMINDER_LOCATION).getValue<String>(),
                color = child(Constants.REMINDER_COLOR).getValue<Int>(),
                date = child(Constants.REMINDER_DATE).getValue<Long>()
            )
        }
    }
}