package io.techmeskills.an02onl_plannerapp.screen.main.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.techmeskills.an02onl_plannerapp.BuildConfig
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import io.techmeskills.an02onl_plannerapp.screen.main.models.User


@Database(
    entities = [
        Note::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)

abstract class MyAppDataBase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun usersDao(): UsersDao
}

object DataBaseConstructor {
    fun create(context: Context): MyAppDataBase =
        Room.databaseBuilder(
            context,
            MyAppDataBase::class.java,
            "${BuildConfig.APPLICATION_ID}.db"
        ).build()
}
