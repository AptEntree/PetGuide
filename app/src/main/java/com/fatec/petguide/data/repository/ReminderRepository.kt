package com.fatec.petguide.data.repository

import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.data.util.Constants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase

class ReminderRepository(private val userId: String) {

    fun createReminder(reminderEntity: ReminderEntity) {
        with(
            FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
                .child(Constants.REMINDER_NODE).push()
        ) {
            child(Constants.REMINDER_TITLE).setValue(reminderEntity.title)
            child(Constants.REMINDER_PET_ID).setValue(reminderEntity.petId)
            child(Constants.REMINDER_CATEGORY).setValue(reminderEntity.category)
            child(Constants.REMINDER_DATE).setValue(reminderEntity.date)
            child(Constants.REMINDER_LOCATION).setValue(reminderEntity.location)
            child(Constants.REMINDER_COLOR).setValue(reminderEntity.color)
        }
    }

    fun getReminderList(valueEventListener: ChildEventListener) {
        FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
            .child(Constants.REMINDER_NODE).addChildEventListener(valueEventListener)
    }

}