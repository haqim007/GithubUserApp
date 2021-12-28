package com.example.githubuserapp.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUsers::class], version=1)
abstract class FavUsersRoom: RoomDatabase(){
    abstract fun favUsersDao(): FavUsersDao

    companion object{
        @Volatile
        private var INSTANCE: FavUsersRoom?  = null

        @JvmStatic
        fun getDatabase(context: Context): FavUsersRoom{
            if(INSTANCE == null){
                synchronized(FavUsersRoom::class.java)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUsersRoom::class.java, "github_users_database"
                    ).build()
                }
            }

            return INSTANCE as FavUsersRoom
        }
    }
}