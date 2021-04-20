package io.techmeskills.an02onl_plannerapp.screen.main.repositories

import io.techmeskills.an02onl_plannerapp.screen.main.database.UsersDao
import io.techmeskills.an02onl_plannerapp.screen.main.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.screen.main.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val usersDao: UsersDao, private val appSettings: AppSettings) {

    suspend fun login(userName: String, password: String) {
        withContext(Dispatchers.IO) {
            if (checkUserExists(userName).not()) {
                val userId = usersDao.addNewUser(User(name = userName, password = password))
                appSettings.setUserId(userId)
            } else {
                val userId = usersDao.getUserId(userName)
                appSettings.setUserId(userId)
            }
        }
    }

    private suspend fun checkUserExists(userName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUsersCount(userName) > 0
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            appSettings.setUserId(-1)
        }
    }
}