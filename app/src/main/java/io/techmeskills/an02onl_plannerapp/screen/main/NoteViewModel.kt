package io.techmeskills.an02onl_plannerapp.screen.main

import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    fun addNewNote(note: Note) {
        launch {
            notesRepository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesRepository.updateNote(note)
        }
    }
}