package com.fatec.petguide.data.entity

data class ReminderEntity(
    val reminderId: String?,
    val title: String?,
    val petId: String?,
    val category: String?,
    val date: String?,
    val location: String?,
    val color: Int?
)