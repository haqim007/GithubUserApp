package com.example.githubuserapp.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUsers)

    @Update
    fun update(favUser: FavUsers)

    @Delete
    fun delete(favUser: FavUsers)

    @Query("SELECT * FROM FavUsers ORDER BY id ASC")
    fun getAllFavUsers(): LiveData<List<FavUsers>>

    @Query("SELECT * FROM FavUsers WHERE FavUsers.login = :username ORDER BY id ASC")
    fun getAllFavUsersByUsername(username: String): LiveData<FavUsers>
}