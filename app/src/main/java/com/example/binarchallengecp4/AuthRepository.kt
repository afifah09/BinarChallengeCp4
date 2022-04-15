package com.example.binarchallengecp4

import android.content.Context
import com.binar.challenge4.data.User
import com.binar.challenge4.database.MyDatabase
import kotlinx.coroutines.coroutineScope

class AuthRepository (context: Context) {
    val myDatabase = MyDatabase.getInstance(context)
    suspend fun login(email: String, password: String): User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.login(email, password)
    }
    suspend fun insertUser(user: User):Long? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.insertUser(user)
    }
}