package com.example.githubuserapp.viewModels

import androidx.lifecycle.*
import com.example.githubuserapp.helpers.data.SettingPreferences
import com.example.githubuserapp.models.SearchUsersResponse
import com.example.githubuserapp.models.UsersResponseItem
import com.example.githubuserapp.services.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {
    val usersList: LiveData<MutableList<UsersResponseItem>>
        get() = _usersList
    val isSucceed: LiveData<Boolean>
        get() = _isSucceed
    private var _usersList = MutableLiveData<MutableList<UsersResponseItem>>().apply {
        value = null
    }
    private var _isSucceed = MutableLiveData<Boolean>().apply {
        value = true
    }

    fun getUsers(){
        val fetchUsers = ApiConfig.getApiService().getUsers()
        fetchUsers.enqueue(
            object : Callback<MutableList<UsersResponseItem>>{
                override fun onResponse(
                    call: Call<MutableList<UsersResponseItem>>,
                    response: Response<MutableList<UsersResponseItem>>
                ) {
                    val respBody = response.body()
                    if(response.isSuccessful){
                        _usersList.value = respBody
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

    fun getSearchUsers(query: String){
        val fetchUsers = ApiConfig.getApiService().getSearchUsers(query)
        fetchUsers.enqueue(
            object : Callback<SearchUsersResponse>{
                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>
                ) {
                    val respBody = response.body()
                    if(response.isSuccessful){
                        if (respBody != null) {
                            _usersList.value = respBody.items
                        }
                    }else{
                        _isSucceed.value = false
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    _isSucceed.value = false
                }

            }
        )
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}