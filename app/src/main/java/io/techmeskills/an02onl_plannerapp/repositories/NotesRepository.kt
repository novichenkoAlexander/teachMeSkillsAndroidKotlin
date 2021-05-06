package io.techmeskills.an02onl_plannerapp.repositories

import io.techmeskills.an02onl_plannerapp.database.NotesDao
import io.techmeskills.an02onl_plannerapp.database.UsersDao
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NotesRepository(
    private val notesDao: NotesDao,
    private val usersDao: UsersDao,
    private val appSettings: AppSettings,
    private val notificationRepository: NotificationRepository
) {


    @ExperimentalCoroutinesApi
    val currentUserNotesFlow: Flow<List<Note>> = appSettings.userNameFlow().flatMapLatest { userName ->
        usersDao.getUserContentFlow(userName).map {
            it?.notes ?: emptyList()
        }
    }

    suspend fun getCurrentUsersNotes(): List<Note> {
        return usersDao.getUserContent(appSettings.getUserName())?.notes ?: emptyList()
    }

    suspend fun setAllNotesSyncWithCloud() {
        withContext(Dispatchers.IO) {
            notesDao.setAllNotesSyncWithCloud()
        }
    }

    suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            notificationRepository.setNotification(note)
            notesDao.insertNote(
                Note(
                    title = note.title,
                    date = note.date,
                    time = note.time,
                    userName = appSettings.getUserName()
                )
            )
        }
    }

    suspend fun addNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notesDao.clearAndSaveNotes(notes)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            val oldNote = notesDao.getNoteById(note.id)
            notificationRepository.unsetNotification(oldNote)
            notesDao.updateNote(note)
            notificationRepository.setNotification(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notificationRepository.unsetNotification(note)
            notesDao.deleteNote(note)
        }
    }

}