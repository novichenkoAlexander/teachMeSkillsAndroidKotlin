package io.techmeskills.an02onl_plannerapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNotes(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    abstract fun clearTable()

    @Transaction
    open fun clearAndSaveNotes(notes: List<Note>) {
        clearTable()
        insertNotes(notes)
    }

    @Update
    abstract fun updateNote(note: Note)

    @Query("SELECT * FROM notes")
    abstract fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes")
    abstract fun getAllNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes")
    abstract fun getAllNotesLiveData(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE userId ==:userId ORDER BY id DESC")
    abstract fun getAllNotesByUserId(userId: Long): List<Note>

    @Query("SELECT * FROM notes WHERE userId ==:userId ORDER BY id DESC")
    abstract fun getAllNotesFlowByUserId(userId: Long): Flow<List<Note>>

    @Query("UPDATE notes SET fromCloud = 1")
    abstract fun setAllNotesSyncWithCloud()
}