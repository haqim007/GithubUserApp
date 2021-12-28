package com.example.githubuserapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavUsersViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var INSTANCE: FavUsersViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavUsersViewModelFactory{
            if(INSTANCE == null){
                synchronized(FavUsersViewModelFactory::class.java){
                    INSTANCE = FavUsersViewModelFactory(application)
                }
            }

            return INSTANCE as FavUsersViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(UsersFavoriteViewModel::class.java)) {
            return UsersFavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}