package com.example.githubuserapp.models

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("UsersResponse")
	val usersResponse: MutableList<UsersResponseItem?>? = null
)

data class UsersResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)
