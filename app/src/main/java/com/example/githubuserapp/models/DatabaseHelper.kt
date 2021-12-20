package com.example.githubuserapp.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoriteusersapp"
        private const val DATABASE_VERSION = 3
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE ${DatabaseContract.UserFavoriteColumns.TABLE_NAME}" +
                " (${DatabaseContract.UserFavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserFavoriteColumns.ID} INTEGER NOT NULL," +
                " ${DatabaseContract.UserFavoriteColumns.LOGIN} TEXT NOT NULL," +
                " ${DatabaseContract.UserFavoriteColumns.AVATAR_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UserFavoriteColumns.TABLE_NAME}")
        onCreate(db)
    }
}