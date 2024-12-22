package com.example.passwordmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanager.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun get(userId: Int) : User

    @Update
    fun update(user: User)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?
}