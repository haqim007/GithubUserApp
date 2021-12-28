package com.example.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ItemCardUserBinding
import com.example.githubuserapp.helpers.data.FavUsersDiffCallback
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.models.UsersResponseItem

class ListUsersFavoriteAdapter(
    val onClickListener: (username:String) -> Unit
): RecyclerView.Adapter<ListUsersFavoriteAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCardUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var userPhoto: ImageView = binding.ivUserPhoto
        var username: TextView = binding.tvUserName
    }

    private val listFavUsers = ArrayList<FavUsers>()
    fun setListFavUsers(listFavUsers: List<FavUsers>) {
        val diffCallback = FavUsersDiffCallback(this.listFavUsers, listFavUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUsers.clear()
        this.listFavUsers.addAll(listFavUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatarUrl = listFavUsers[position].avatarUrl
        val login = listFavUsers[position].login
        Glide
            .with(holder.itemView.context)
            .load(avatarUrl)
            .placeholder(R.drawable.github_img)
            .circleCrop()
            .into(holder.userPhoto)
        holder.username.text = holder.itemView.context.resources.getString(R.string.username_value).format(login)
        holder.itemView.setOnClickListener {
            if (login != null) {
                onClickListener(login)
            }
        }
    }

    override fun getItemCount(): Int = listFavUsers?.size ?: 0




}