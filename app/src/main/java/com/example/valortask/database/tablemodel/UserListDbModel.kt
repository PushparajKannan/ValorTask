package com.example.valortask.database.tablemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class UserListDbModel (
    @PrimaryKey
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String,
    @ColumnInfo(name = "userName")
    var userName: String?,
    @ColumnInfo(name = "userMail")
    var userMail: String?,
    @ColumnInfo(name = "userDateOfBirth")
    var userDateOfBirth: String?,
    @ColumnInfo(name = "userPassword")
    var userPassword: String?
)


