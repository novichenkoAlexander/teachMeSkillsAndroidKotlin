package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.screen.main.database.NotesDao
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(private val notesDao: NotesDao) : CoroutineViewModel() {

    val notesLiveDao = notesDao.getAllNotesFlow().map { it }.flowOn(Dispatchers.IO).asLiveData()

    fun deleteNote(pos: Int) {
        launch {
            val note = notesLiveDao.value?.get(pos)
            note?.let { notesDao.deleteNote(it) }
        }
    }

}
