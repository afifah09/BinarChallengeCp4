package com.example.binarchallengecp4

import android.content.Context
import com.binar.challenge4.data.Schedule
import com.binar.challenge4.database.MyDatabase
import kotlinx.coroutines.coroutineScope

class ScheduleRepository(context: Context) {
    val myDatabase = MyDatabase.getInstance(context)
    suspend fun getAllSchedule():List<Schedule>? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.getAllSchedule()
    }
    suspend fun insertSchedule(schedule: Schedule):Long? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.insertSchedule(schedule)
    }
    suspend fun updateSchedule(schedule: Schedule):Int? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.updateSchedule(schedule)
    }
    suspend fun deleteSchedule(schedule: Schedule):Int? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.deleteSchedule(schedule)
    }
    suspend fun getAllPeserta():List<Peserta>? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.getAllPeserta()
    }
    suspend fun insertPeserta(peserta: Peserta):Long? = coroutineScope {
        return@coroutineScope myDatabase?.scheduleDao()?.insertPeserta(peserta)
    }
}