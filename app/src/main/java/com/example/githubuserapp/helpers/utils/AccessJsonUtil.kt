package com.example.githubuserapp.helpers.utils

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

class AccessJsonUtil <E> {
    private fun getJson(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun readJson(context: Context, fileName: String, model: Class<E>): E {
        val jsonFileString = getJson(context, fileName)
        val gson = Gson()
        return gson.fromJson(jsonFileString, model)
    }
}