package com.fatec.petguide.data.repository

import com.fatec.petguide.data.entity.HistoricEntity
import com.fatec.petguide.data.entity.PetEntity
import com.fatec.petguide.data.util.Constants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase

class PetRepository(private val userId: String) {

    fun createPet(petEntity: PetEntity) {
        with(
            FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
                .child(Constants.PET_NODE).push()
        ) {
            child(Constants.PET_NAME).setValue(petEntity.name)
            child(Constants.PET_AGE).setValue(petEntity.age)
            child(Constants.PET_RACE).setValue(petEntity.race)
            child(Constants.PET_COAT).setValue(petEntity.coat)
        }
    }

    fun getPetList(valueEventListener: ChildEventListener) {
        FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
            .child(Constants.PET_NODE).addChildEventListener(valueEventListener)
    }

    fun createHistoric(petId: String, historicEntity: HistoricEntity) {
        with(
            FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
                .child(Constants.PET_NODE).child(petId).child(Constants.HISTORIC_NODE).push()
        ) {
            child(Constants.HISTORIC_TITLE).setValue(historicEntity.title)
            child(Constants.HISTORIC_DATE).setValue(historicEntity.date)
            child(Constants.HISTORIC_FILE).setValue(historicEntity.file)
        }
    }

    fun getHistoricList(petId: String, valueEventListener: ChildEventListener) {
        FirebaseDatabase.getInstance().reference.child(Constants.USER_NODE).child(userId)
            .child(Constants.PET_NODE).child(petId).child(Constants.HISTORIC_NODE)
            .addChildEventListener(valueEventListener)
    }
}