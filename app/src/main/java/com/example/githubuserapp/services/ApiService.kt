package com.example.githubuserapp.services

import com.example.githubuserapp.models.DetailUserResponse
import com.example.githubuserapp.models.SearchUsersResponse
import com.example.githubuserapp.models.UsersResponse
import com.example.githubuserapp.models.UsersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(): Call<MutableList<UsersResponseItem>>

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") q: String?
    ): Call<SearchUsersResponse>

    @GET("users/{username}")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<MutableList<UsersResponseItem>>

    @GET("users/{username}/following")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<MutableList<UsersResponseItem>>
}