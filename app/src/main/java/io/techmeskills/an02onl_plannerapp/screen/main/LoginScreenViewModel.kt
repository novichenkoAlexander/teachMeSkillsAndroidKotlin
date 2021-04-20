package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.screen.main.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel(private val usersRepository: UsersRepository) : CoroutineViewModel() {

    val errorLiveData = MutableLiveData<String>()

    fun login(userName: String, password: String) {
        launch {
            try {
                if (userName.isNotBlank() && password.isNotBlank()) {
                    usersRepository.login(userName, password)
                } else if (userName.isNotBlank()) {
                    errorLiveData.postValue("Enter password")
                } else if (password.isNotBlank()) {
                    errorLiveData.postValue("Enter user name")
                } else {
                    errorLiveData.postValue("Enter user name and password")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }

    }


}