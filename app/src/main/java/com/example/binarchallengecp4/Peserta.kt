package com.example.binarchallengecp4

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Peserta(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val nama: String,
)