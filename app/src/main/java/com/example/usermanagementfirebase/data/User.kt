package com.example.usermanagementfirebase.data

import java.util.*

data class User(
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val sex: String,
    val birthDate: Long,
    val country: String,
    val state: String,
    val address: String
)
//nombre, apellido, teléfono, correo electrónico, sexo,
//fecha de nacimiento, país, provincia o estado y dirección.
