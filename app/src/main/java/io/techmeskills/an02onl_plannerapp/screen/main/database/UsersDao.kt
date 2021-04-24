package io.techmeskills.an02onl_plannerapp.screen.main.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.techmeskills.an02onl_plannerapp.screen.main.models.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun addNewUser(user: User): Long

    @Query("SELECT COUNT(*) FROM users WHERE name == :userName")
    abstract fun getUsersCount(userName: String): Int

    @Query("SELECT id FROM users WHERE name == :userName")
    abstract fun getUserId(userName: String): Long

    @Query("SELECT password FROM users WHERE name == :userName")
    abstract fun getUserPasswordByName(userName: String): String

    @Query("SELECT name FROM USERS WHERE id ==:userId")
    abstract fun getUserNameByIdFlow(userId: Long): Flow<String>

    @Query("SELECT name FROM users")
    abstract fun getAllUserNames(): Flow<List<String>>
}