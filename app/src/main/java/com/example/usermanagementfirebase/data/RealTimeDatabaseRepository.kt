package com.example.usermanagementfirebase.data

import com.google.firebase.database.ValueEventListener

interface RealTimeDatabaseRepository {
    suspend fun insertUser(
        userId: String,
        name: String,
        lastName: String,
        phoneNumber: String,
        sex: String,
        birthDate: String,
        country: String,
        state: String,
        address: String
    )

    suspend fun getUser(userId: String): User?
}