package io.techmeskills.an02onl_plannerapp.screen.main.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import io.techmeskills.an02onl_plannerapp.screen.main.database.UsersDao
import io.techmeskills.an02onl_plannerapp.screen.main.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.screen.main.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class UsersRepository(context: Context, private val usersDao: UsersDao, private val appSettings: AppSettings) {

    suspend fun login(userName: String, password: String): Boolean {
        var isPasswordCorrect = false
        withContext(Dispatchers.IO) {
            if (checkUserExists(userName).not()) {
                val userId = usersDao.addNewUser(User(name = userName, password = password))
                appSettings.setUserId(userId)
                isPasswordCorrect = true
            } else {
                if (checkUserPasswordIsCorrect(userName, password)) {
                    val userId = usersDao.getUserId(userName)
                    appSettings.setUserId(userId)
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

    fun getCurrentUserName(): Flow<String> = appSettings.userIdFlow().flatMapLatest { userId ->
        usersDao.getUserNameByIdFlow(userId)
    }

    fun getCurrentUserById(): Flow<User> = appSettings.userIdFlow().flatMapLatest { userId ->
        usersDao.getUserById(userId)
    }

    fun checkUserLoggedIn(): Flow<Boolean> =
        appSettings.userIdFlow().map { it >= 0 }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            appSettings.setUserId(-1)
        }
    }

    @SuppressLint("HardwareIds")
    val phoneId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}