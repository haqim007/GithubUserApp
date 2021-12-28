package com.example.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapters.ListUsersFavoriteAdapter
import com.example.githubuserapp.databinding.ActivityUsersFavoriteBinding
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.viewModels.FavUsersViewModelFactory
import com.example.githubuserapp.viewModels.UsersFavoriteViewModel

class UsersFavoriteActivity : AppCompatActivity() {
    private lateinit var viewModel: UsersFavoriteViewModel
    private var _binding: ActivityUsersFavoriteBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(this@UsersFavoriteActivity)
        _binding = ActivityUsersFavoriteBinding.inflate(layoutInflater)
        supportActionBar?.title = resources.getString(R.string.favorite_users)
        showLoading(true)
        viewModel.getUsersFav().observe(this, {
            showRecyclerList(it)
            if(it.isNullOrEmpty()){
                binding?.noFavUsers?.visibility = View.VISIBLE
            }else{
                binding?.noFavUsers?.visibility = View.GONE
            }

            showLoading(false)
        })

        setContentView(binding?.root)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showRecyclerList(list:  List<FavUsers>) {
        binding?.rvUsersFav?.layoutManager = LinearLayoutManager(this)
        val listFavUsersAdapter = ListUsersFavoriteAdapter(::openDetail)
        listFavUsersAdapter.setListFavUsers(list)
        binding?.rvUsersFav?.adapter = listFavUsersAdapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): UsersFavoriteViewModel {
        val factory = FavUsersViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UsersFavoriteViewModel::class.java]
    }

    private fun openDetail(username: String){
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}