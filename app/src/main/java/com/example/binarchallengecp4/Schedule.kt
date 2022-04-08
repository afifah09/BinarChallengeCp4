package com.binar.challenge4.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Schedule(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var judul: String,
    var peserta: String,
    var tanggal: String,
    var deskripsi:String,

): Parcelable
