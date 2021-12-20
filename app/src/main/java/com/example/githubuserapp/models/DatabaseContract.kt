package com.example.githubuserapp.models

import android.provider.BaseColumns
import com.google.gson.annotations.SerializedName

interface DatabaseContract {

    class UserFavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user_favorite"
            const val _ID = "_id"
            const val ID = "id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatarUrl"
        }
    }


}