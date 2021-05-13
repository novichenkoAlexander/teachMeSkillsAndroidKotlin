package io.techmeskills.an02onl_plannerapp.database


import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.Note

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

    @Query("SELECT * FROM notes WHERE id==:noteId")
    abstract fun getNoteById(noteId: Long): Note

    @Query("SELECT * FROM notes")
    abstract fun getAllNotes(): List<Note>

    @Query("UPDATE notes SET fromCloud = 1")
    abstract fun setAllNotesSyncWithCloud()
}