package com.example.githubuserapp.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.githubuserapp.models.DatabaseContract.UserFavoriteColumns.Companion.LOGIN
import com.example.githubuserapp.models.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME
import com.example.githubuserapp.models.DatabaseContract.UserFavoriteColumns.Companion._ID

class FavUsersHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavUsersHelper? = null

        fun getInstance(context: Context): FavUsersHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavUsersHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }

    fun queryByUsername(username: String): Cursor {
        return database.query(
            DATABASE_TABLE, null, "$LOGIN = ?",
            arrayOf(username),
            null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$username'", null)
    }
}