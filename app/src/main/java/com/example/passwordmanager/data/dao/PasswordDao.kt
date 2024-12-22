package com.example.passwordmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanager.data.entity.Password

@Dao
interface PasswordDao {
    @Query("SELECT * FROM password")
    fun getAll(): List<Password>

    @Query("SELECT * FROM password WHERE userId IN (:userIds)")
    fun loadAllByIds(userIds: Int): List<Password>

    @Insert
    fun insertAll(vararg passwords: Password)

    @Delete
    fun delete(password: Password)

    @Query("SELECT * FROM password WHERE userId = :userId")
    fun get(userId: Int) : Password

    @Update
    fun update(password: Password)
}