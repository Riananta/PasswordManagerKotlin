package com.example.passwordmanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// table User
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Int? = null,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "full_name") var fullName: String?,
    @ColumnInfo(name = "password") var password: String?
)