package com.example.githubuserapp.helpers.data

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.models.FavUsers

class FavUsersDiffCallback(
    private val mOldFavUsersList: List<FavUsers>,
    private val mNewFavUsersList: List<FavUsers>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavUsersList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavUsersList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavUsersList[oldItemPosition].id == mNewFavUsersList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldFavUsersList[oldItemPosition]
        val newUser = mNewFavUsersList[newItemPosition]
        return oldUser.login == newUser.login && oldUser.avatarUrl == newUser.avatarUrl
    }

}