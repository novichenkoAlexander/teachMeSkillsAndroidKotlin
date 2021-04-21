package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel(private val usersRepository: UsersRepository) : CoroutineViewModel() {

    val errorLiveData = MutableLiveData<String>()

    val loggedIn: LiveData<Boolean> = usersRepository.checkUserLoggedIn().asLiveData()

    fun login(userName: String, password: String) {
        launch {
            try {
                if (userName.isNotBlank() && password.isNotBlank()) {
                    if (!usersRepository.login(userName, password)) {
                        errorLiveData.postValue("Wrong password")
                    }
                } else if (userName.isNotBlank()) {
                    errorLiveData.postValue("Enter password")
                } else if (password.isNotBlank()) {
                    errorLiveData.postValue("Enter user name")
                } else if (userName.isBlank() && password.isBlank()) {
                    errorLiveData.postValue("Enter user name and password")
                } else {
                    errorLiveData.postValue("Wrong password")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }

}