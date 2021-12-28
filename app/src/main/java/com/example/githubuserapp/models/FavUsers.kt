package com.example.githubuserapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavUsers(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "github_id")
    var githubId: Int? = null,

    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null
)