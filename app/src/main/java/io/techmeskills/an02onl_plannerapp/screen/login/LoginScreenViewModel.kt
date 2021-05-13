package io.techmeskills.an02onl_plannerapp.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel(private val usersRepository: UsersRepository) : CoroutineViewModel() {

    val errorLiveData = MutableLiveData<String>()

    val loggedIn: LiveData<Boolean> = usersRepository.checkUserLoggedIn().asLiveData()

    val autoCompleteUserNameLiveData = usersRepository.getAllUsersNames().asLiveData()

    fun login(userName: String, password: String) {
        launch {
            try {
                when {
                    userName.isNotBlank() && password.isNotBlank() ->
                        if (!usersRepository.login(userName, password)) {
                            errorLiveData.postValue("Wrong password")
                        }
                    userName.isNotBlank() -> errorLiveData.postValue("Enter password")
                    password.isNotBlank() -> errorLiveData.postValue("Enter user name")
                    userName.isBlank() && password.isBlank() ->
                        errorLiveData.postValue("Enter user name and password")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }

}