package io.techmeskills.an02onl_plannerapp.screen.settings

import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(private val usersRepository: UsersRepository) : CoroutineViewModel() {

    fun logOut() {
        launch {
            usersRepository.logout()
        }
    }

    fun deleteUser() {
        launch {
            usersRepository.deleteCurrentUser()
        }
    }
}