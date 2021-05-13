package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.repositories.CloudRepository
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MainViewModel(
    private val notesRepository: NotesRepository,
    usersRepository: UsersRepository,
    private val cloudRepository: CloudRepository
) : CoroutineViewModel() {

    val currentUserNameLiveData = usersRepository.getCurrentUserName().asLiveData()

    @ExperimentalCoroutinesApi
    val notesLiveData = notesRepository.currentUserNotesFlow.asLiveData()

    val progressLifeData = MutableLiveData<Boolean>()

    @ExperimentalCoroutinesApi
    fun deleteNote(pos: Int) {
        launch {
            val note = notesLiveData.value?.get(pos)
            note?.let { notesRepository.deleteNote(it) }
        }
    }

    fun exportNotes() = launch {
        val result = cloudRepository.exportNotes()
        progressLifeData.postValue(result)
    }

    fun importNotes() = launch {
        val result = cloudRepository.importNotes()
        progressLifeData.postValue(result)
    }

}
