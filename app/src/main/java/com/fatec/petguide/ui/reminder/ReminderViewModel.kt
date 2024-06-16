package com.fatec.petguide.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.data.repository.PetRepository
import com.fatec.petguide.data.repository.ReminderRepository
import com.fatec.petguide.data.util.Constants
import com.fatec.petguide.ui.base.BaseViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReminderViewModel : BaseViewModel() {

    private val reminderRepository = ReminderRepository(accountRepository.getCurrentUserId())
    private val petRepository = PetRepository(accountRepository.getCurrentUserId())

    private var _reminderListData: MutableLiveData<List<ReminderEntity>> = MutableLiveData()
    val reminderListData: LiveData<List<ReminderEntity>> get() = _reminderListData

    private var _reminderListForTodayData: MutableLiveData<List<ReminderEntity>> = MutableLiveData()
    val reminderListForTodayData: LiveData<List<ReminderEntity>> get() = _reminderListForTodayData

    private var _petListData: MutableLiveData<List<String?>> = MutableLiveData()
    val petListData: LiveData<List<String?>> get() = _petListData

    fun getReminderList() {
        reminderRepository.getReminderList(object : ChildEventListener {
            val list: MutableList<ReminderEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (isNewer(snapshot.child(Constants.REMINDER_DATE).getValue<String>())) {
                        list.add(createReminderEntity(snapshot))
                    }
                }
                _reminderListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun tryCreateReminder(
        reminderEntity: ReminderEntity
    ) {
        reminderRepository.createReminder(reminderEntity)
    }

    fun getPartialReminderList(text: CharSequence) {
        reminderRepository.getReminderList(object : ChildEventListener {
            val list: MutableList<ReminderEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (snapshot.child(Constants.REMINDER_TITLE).getValue<String>()
                            ?.contains(text) == true
                    ) {
                        list.add(createReminderEntity(snapshot))
                    }
                }
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
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(Calendar.getInstance().time)
                    if (formattedDate == snapshot.child(Constants.REMINDER_DATE)
                            .getValue<String>()
                    ) {
                        list.add(createReminderEntity(snapshot))
                    }
                }
                _reminderListForTodayData.postValue(list)
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
                date = child(Constants.REMINDER_DATE).getValue<String>()
            )
        }
    }

    fun getPetNameList() {
        petRepository.getPetList(object : ChildEventListener {
            val list: MutableList<String?> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    list.add(snapshot.child(Constants.PET_NAME).getValue<String>())
                }
                _petListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun deleteReminder(reminderId: String?) {
        if (reminderId != null) {
            reminderRepository.deleteReminder(reminderId)
        }
    }

    private fun isNewer(value: String?): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Calendar.getInstance().time)

        val splitTested = (value?: "").split("/")
        val splitTester = formattedDate.split("/")

        if (splitTested.size == 3) {
            return if (splitTested[2].toInt() < splitTester[2].toInt()) {
                false
            } else if (splitTested[1].toInt() < splitTester[1].toInt()) {
                false
            } else splitTested[0].toInt() >= splitTester[0].toInt()
        }
        return true
    }
}