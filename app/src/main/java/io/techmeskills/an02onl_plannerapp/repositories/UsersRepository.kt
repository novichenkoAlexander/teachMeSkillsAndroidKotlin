package io.techmeskills.an02onl_plannerapp.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import io.techmeskills.an02onl_plannerapp.database.UsersDao
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class UsersRepository(context: Context, private val usersDao: UsersDao, private val appSettings: AppSettings) {

    suspend fun login(userName: String, password: String): Boolean {
        var isPasswordCorrect = false
        withContext(Dispatchers.IO) {
            if (checkUserExists(userName).not()) {
                usersDao.addNewUser(User(name = userName, password = password))
                appSettings.setUserName(userName)
                isPasswordCorrect = true
            } else {
                if (checkUserPasswordIsCorrect(userName, password)) {
                    appSettings.setUserName(userName)
                    isPasswordCorrect = true
                } else {
                    isPasswordCorrect
                }
            }
        }
        return isPasswordCorrect
    }

    private suspend fun checkUserPasswordIsCorrect(userName: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUserPasswordByName(userName) == password
        }
    }

    private suspend fun checkUserExists(userName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUsersCount(userName) > 0
        }
    }

    fun getCurrentUserName(): Flow<String> = appSettings.userNameFlow().map { it }

    fun getCurrentUserById(): Flow<User> = appSettings.userNameFlow().map { User(it, it) }


    fun checkUserLoggedIn(): Flow<Boolean> =
        appSettings.userNameFlow().map { it.isNotEmpty() }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            appSettings.setUserName("")
        }
    }

    suspend fun deleteCurrentUser() {
        withContext(Dispatchers.IO) {
            usersDao.deleteUser(User(appSettings.getUserName(), ""))
            logout()
        }
    }

    fun getAllUsersNames() = usersDao.getAllUserNames()

    @SuppressLint("HardwareIds")
    val phoneId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}