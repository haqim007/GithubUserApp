package com.example.githubuserapp.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.repository.FavUsersRepository

class UsersFavoriteViewModel(application: Application): ViewModel() {

    private val mFavUsersRepository: FavUsersRepository = FavUsersRepository(application)

    fun getUsersFav(): LiveData<List<FavUsers>> = mFavUsersRepository.getAllFavUsers()
}