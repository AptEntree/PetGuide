package com.fatec.petguide.ui.pet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatec.petguide.data.entity.HistoricEntity
import com.fatec.petguide.data.entity.PetEntity
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.data.repository.PetRepository
import com.fatec.petguide.data.util.Constants
import com.fatec.petguide.ui.base.BaseViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue

class PetViewModel : BaseViewModel() {

    private val petRepository = PetRepository(accountRepository.getCurrentUserId())

    private var _petListData: MutableLiveData<List<PetEntity>> = MutableLiveData()
    val petListData: LiveData<List<PetEntity>> get() = _petListData

    private var _petHistoricListData: MutableLiveData<List<HistoricEntity>> = MutableLiveData()
    val petHistoricListData: LiveData<List<HistoricEntity>> get() = _petHistoricListData

    fun getPetList() {
        petRepository.getPetList(object : ChildEventListener {
            val list: MutableList<PetEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    list.add(createPetEntity(snapshot))
                }
                _petListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getPartialPetList(text: CharSequence) {
        petRepository.getPetList(object : ChildEventListener {
            val list: MutableList<PetEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (snapshot.child(Constants.PET_NAME).getValue<String>()?.contains(text) == true) {
                        list.add(createPetEntity(snapshot))
                    }
                }
                _petListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun createPetEntity(snapshot: DataSnapshot): PetEntity {
        return with(snapshot) {
            PetEntity(
                petId = key,
                name = child(Constants.PET_NAME).getValue<String>(),
                age = child(Constants.PET_AGE).getValue<String>(),
                coat = child(Constants.PET_COAT).getValue<String>(),
                race = child(Constants.PET_RACE).getValue<String>()
            )
        }
    }

    fun getPetHistoricList(petId: String) {
        petRepository.getHistoricList(petId, object : ChildEventListener {
            val list: MutableList<HistoricEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    list.add(createPetHistoricEntity(snapshot))
                }
                _petHistoricListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getPetPartialHistoricList(petId: String, text: CharSequence) {
        petRepository.getHistoricList(petId, object : ChildEventListener {
            val list: MutableList<HistoricEntity> = mutableListOf()
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (snapshot.child(Constants.HISTORIC_TITLE).getValue<String>()?.contains(text) == true) {
                        list.add(createPetHistoricEntity(snapshot))
                    }
                }
                _petHistoricListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun createPetHistoricEntity(snapshot: DataSnapshot): HistoricEntity {
        return with(snapshot) {
            HistoricEntity(
                historicId = key,
                title = child(Constants.HISTORIC_TITLE).getValue<String>(),
                file = child(Constants.HISTORIC_FILE).getValue<String>(),
                date = child(Constants.HISTORIC_DATE).getValue<String>()
            )
        }
    }


    fun tryCreatePet(
        petEntity: PetEntity
    ) {
        petRepository.createPet(petEntity)
    }

    fun tryCreatePetHistoric(
        petId: String,
        historicEntity: HistoricEntity
    ) {
        petRepository.createHistoric(petId, historicEntity)
    }
}