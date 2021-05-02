package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.CloudRepository
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val notesRepository: NotesRepository,
    private val usersRepository: UsersRepository,
    private val cloudRepository: CloudRepository
) : CoroutineViewModel() {

    val currentUserNameLiveData = usersRepository.getCurrentUserName().asLiveData()

    val notesLiveData = notesRepository.currentUserNotesFlow.asLiveData()

    val progressLifeData = MutableLiveData<Boolean>()

    fun deleteNote(pos: Int) {
        launch {
            val note = notesLiveData.value?.get(pos)
            note?.let { notesRepository.deleteNote(it) }
        }
    }

    fun logOut() {
        launch {
            usersRepository.logout()
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
