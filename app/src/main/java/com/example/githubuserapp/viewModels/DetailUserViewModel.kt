package com.example.githubuserapp.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.models.DetailUserResponse
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.models.UsersResponseItem
import com.example.githubuserapp.repository.FavUsersRepository
import com.example.githubuserapp.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): ViewModel() {
    val usersDetail: LiveData<DetailUserResponse>
        get() = _usersDetail
    val isSucceed: LiveData<Boolean>
        get() = _isSucceed
    private var _usersDetail= MutableLiveData<DetailUserResponse>().apply {
        value = null
    }
    private var _isSucceed = MutableLiveData<Boolean>().apply {
        value = true
    }
    val followersList: LiveData<MutableList<UsersResponseItem>>
        get() = _followersList
    private var _followersList = MutableLiveData<MutableList<UsersResponseItem>>().apply {
        value = null
    }
    val followingList: LiveData<MutableList<UsersResponseItem>>
        get() = _followingList
    private var _followingList = MutableLiveData<MutableList<UsersResponseItem>>().apply {
        value = null
    }

    fun getUsersDetail(username: String){
        val fetchUsersDetail = ApiConfig.getApiService().getUsersDetail(username)
        fetchUsersDetail.enqueue(
            object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    val respBody = response.body()
                    if(response.isSuccessful){
                        _usersDetail.value = respBody
                    }else{
                        _isSucceed.value = false
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isSucceed.value = false
                }

            }
        )
    }

    fun getFollowers(username: String){
        val fetchFollowers = ApiConfig.getApiService().getUsersFollowers(username)
        fetchFollowers.enqueue(
            object : Callback<MutableList<UsersResponseItem>>{
                override fun onResponse(
                    call: Call<MutableList<UsersResponseItem>>,
                    response: Response<MutableList<UsersResponseItem>>
                ) {
                    val respBody = response.body()
                    if(response.isSuccessful){
                        _followersList.value = respBody
                    }else{
                        _isSucceed.value = false
                    }
                }

                override fun onFailure(call: Call<MutableList<UsersResponseItem>>, t: Throwable) {
                    _isSucceed.value = false
                }

            }
        )
    }

    fun getFollowing(username: String){
        val fetchFollowers = ApiConfig.getApiService().getUsersFollowing(username)
        fetchFollowers.enqueue(
            object : Callback<MutableList<UsersResponseItem>>{
                override fun onResponse(
                    call: Call<MutableList<UsersResponseItem>>,
                    response: Response<MutableList<UsersResponseItem>>
                ) {
                    val respBody = response.body()
                    if(response.isSuccessful){
                        _followingList.value = respBody
                    }else{
                        _isSucceed.value = false
                    }
                }

                override fun onFailure(call: Call<MutableList<UsersResponseItem>>, t: Throwable) {
                    _isSucceed.value = false
                }

            }
        )
    }

    private val mFavUsersRepository: FavUsersRepository = FavUsersRepository(application)

    fun insert(favUsers: FavUsers) {
        mFavUsersRepository.insert(favUsers)
    }
    fun update(favUsers: FavUsers) {
        mFavUsersRepository.update(favUsers)
    }
    fun delete(favUsers: FavUsers) {
        mFavUsersRepository.delete(favUsers)
    }
    fun getByUsername(username: String): LiveData<FavUsers> {
       return mFavUsersRepository.getFavUsersByUsername(username)
    }


}