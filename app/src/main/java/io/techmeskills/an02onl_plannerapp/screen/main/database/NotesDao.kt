package io.techmeskills.an02onl_plannerapp.screen.main.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNote(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Update
    abstract fun updateNote(note: Note)

    @Query("SELECT * FROM notes")
    abstract fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes")
    abstract fun getAllNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes")
    abstract fun getAllNotesLiveData(): LiveData<List<Note>>
}