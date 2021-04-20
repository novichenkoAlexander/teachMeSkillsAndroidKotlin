package io.techmeskills.an02onl_plannerapp.screen.main.repositories

import io.techmeskills.an02onl_plannerapp.screen.main.database.NotesDao
import io.techmeskills.an02onl_plannerapp.screen.main.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao, private val appSettings: AppSettings) {


    val currentUserNotesFlow: Flow<List<Note>> = appSettings.userIdFlow().flatMapLatest { userId ->
        notesDao.getAllNotesFlowByUserId(userId)
    }

    suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.insertNote(
                Note(
                    title = note.title,
                    date = note.date,
                    userId = appSettings.getUserId()
                )
            )
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
        }
    }

}