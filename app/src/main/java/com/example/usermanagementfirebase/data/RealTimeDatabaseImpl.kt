package com.example.usermanagementfirebase.data

import com.google.firebase.database.FirebaseDatabase

class RealTimeDatabaseImpl : RealTimeDatabaseRepository {
    private val database = FirebaseDatabase.getInstance().getReference("users")

    override suspend fun insertUser(
        userId: String,
        name: String,
        lastName: String,
        phoneNumber: String,
        sex: String,
        birthDate: String,
        country: String,
        state: String,
        address: String
    ) {
        val userInfo = User(name, lastName, phoneNumber, sex, birthDate, country, state, address)
        database
            .child(userId)
            .setValue(userInfo)
    }

    override suspend fun getUser(userId: String): User? {
        var userData: User? = null
        database.child(userId).get().addOnSuccessListener {
            val name = it.child("name").value.toString()
            val lastName = it.child("lastName").value.toString()
            val phoneNumber = it.child("phoneNumber").value.toString()
            val sex = it.child("sex").value.toString()
            val birthDate = it.child("birthDate").value.toString()
            val country = it.child("country").value.toString()
            val state = it.child("state").value.toString()
            val address = it.child("address").value.toString()
            userData = User(
                name,
                lastName,
                phoneNumber,
                sex,
                birthDate,
                country,
                state,
                address
            )
            println(userData)
        }
        return userData
    }
}