package io.techmeskills.an02onl_plannerapp.screen.main.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
open class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val date: String,
    val userId: Long = -1L,
    val fromCloud: Boolean = false
) : Parcelable
