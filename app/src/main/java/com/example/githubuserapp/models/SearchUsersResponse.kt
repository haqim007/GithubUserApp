package com.example.githubuserapp.models

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: MutableList<UsersResponseItem>? = null
)
