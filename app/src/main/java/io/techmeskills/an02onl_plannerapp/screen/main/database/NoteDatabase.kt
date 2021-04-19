package io.techmeskills.an02onl_plannerapp.screen.main.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note


@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}

object DataBaseConstructor {
    fun create(context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "io.techmeskills.an02onl_plannerapp.db"
        ).build()
}
