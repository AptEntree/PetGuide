package com.fatec.petguide.data.entity

data class UserEntity (
    val id: Int? = null,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val password: String
)