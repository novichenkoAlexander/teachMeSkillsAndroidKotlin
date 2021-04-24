package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val notesRepository: NotesRepository,
    private val usersRepository: UsersRepository
) : CoroutineViewModel() {

    val currentUserNameLiveData = usersRepository.getCurrentUserName().asLiveData()

    val notesLiveData = notesRepository.currentUserNotesFlow.asLiveData()

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

}
