package com.binar.challenge4.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.binar.challenge4.data.Schedule
import com.example.binarchallengecp4.Peserta

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule ")
    fun getAllSchedule():List<Schedule>

    @Insert(onConflict = REPLACE)
    fun insertSchedule(schedule: Schedule):Long

    @Update
    fun updateSchedule(schedule: Schedule):Int

    @Delete
    fun deleteSchedule(schedule: Schedule):Int

    @Query("SELECT * FROM Peserta ")
    fun getAllPeserta():List<Peserta>

    @Insert(onConflict = REPLACE)
    fun insertPeserta(peserta: Peserta):Long
}