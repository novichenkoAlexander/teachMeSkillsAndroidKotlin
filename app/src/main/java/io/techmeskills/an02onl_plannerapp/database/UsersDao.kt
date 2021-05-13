package io.techmeskills.an02onl_plannerapp.database

import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.User
import io.techmeskills.an02onl_plannerapp.models.UserContent
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun addNewUser(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE name == :userName")
    abstract fun getUsersCount(userName: String): Int

    @Query("SELECT password FROM users WHERE name == :userName")
    abstract fun getUserPasswordByName(userName: String): String

    @Query("SELECT name FROM users")
    abstract fun getAllUserNames(): Flow<List<String>>

    @Transaction
    @Query("SELECT * from users WHERE name == :userName LIMIT 1")
    abstract fun getUserContent(userName: String): UserContent?

    @Transaction
    @Query("SELECT * from users WHERE name == :userName LIMIT 1")
    abstract fun getUserContentFlow(userName: String): Flow<UserContent?>

    @Delete
    abstract fun deleteUser(user: User)
}