package com.example.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.models.FavUsersDao
import com.example.githubuserapp.models.FavUsersRoom
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUsersRepository(application: Application) {
    private val mFavUsersDao: FavUsersDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUsersRoom.getDatabase(application)
        mFavUsersDao = db.favUsersDao()
    }

    fun getAllFavUsers(): LiveData<List<FavUsers>> = mFavUsersDao.getAllFavUsers()

    fun insert(favUsers: FavUsers) {
        executorService.execute { mFavUsersDao.insert(favUsers) }
    }
    fun delete(favUsers: FavUsers) {
        executorService.execute { mFavUsersDao.delete(favUsers) }
    }
    fun update(favUsers: FavUsers) {
        executorService.execute { mFavUsersDao.update(favUsers) }
    }

    fun getFavUsersByUsername(username:String): LiveData<FavUsers> =
        mFavUsersDao.getAllFavUsersByUsername(username)
}