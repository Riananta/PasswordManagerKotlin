package com.example.passwordmanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// table User
@Entity
data class Password(
    @PrimaryKey(autoGenerate = true) var passwordId: Int? = null,
    @ColumnInfo(name = "userid") var userId: Int?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "password") var password: String?
)