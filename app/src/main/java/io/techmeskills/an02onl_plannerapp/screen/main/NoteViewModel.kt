package io.techmeskills.an02onl_plannerapp.screen.main

import io.techmeskills.an02onl_plannerapp.screen.main.database.NotesDao
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class NoteViewModel(private val notesDao: NotesDao) : CoroutineViewModel() {

    fun addNewNote(note: Note) {
        launch {
            notesDao.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesDao.updateNote(note)
        }
    }
}