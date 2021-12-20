package com.example.githubuserapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapters.DetailUserPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.models.DatabaseContract
import com.example.githubuserapp.models.DetailUserResponse
import com.example.githubuserapp.models.FavUsersHelper
import com.example.githubuserapp.viewModels.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var favUsersHelper: FavUsersHelper
    private var detailUser: DetailUserResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.detail_user)

        favUsersHelper = FavUsersHelper.getInstance(applicationContext)
        favUsersHelper.open()

        detailUserViewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            showLoading(true)
            detailUserViewModel.getUsersDetail(username)

            val sectionPagerAdapter = DetailUserPagerAdapter(this, username)
            val viewPager = binding.viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs = binding.tlDetailUser
            TabLayoutMediator(tabs, viewPager){ tab: TabLayout.Tab, position: Int ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        detailUserViewModel.usersDetail.observe(this, {
            if (it != null){

                Glide
                    .with(this)
                    .load(it.avatarUrl)
                    .placeholder(R.drawable.github_img)
                    .circleCrop()
                    .into(binding.ivUserPhoto)
                binding.tvUserFullName.text = (if (it.name != null){
                    resources.getString(R.string.user_full_name_value).format(it.name, it.login)
                }else{
                    resources.getString(R.string.username_value).format(it.login)
                }).toString()
                binding.tvCompanyLocation.text = resources.getString(R.string.company_location_value).format(it.company, it.location)
                binding.tvRepository.text = resources.getString(R.string.repositories_value).format(it.publicRepos)
                binding.tvFollowers.text = resources.getString(R.string.followers_value).format(it.followers)
                binding.tvFollowings.text = resources.getString(R.string.followings_value).format(it.following)
                showLoading(false)

                it.login?.let { it1 ->
                    val test = favUsersHelper.queryByUsername(it1)
                    Log.i("testxx", test.toString())

                }



            }

        })

        detailUserViewModel.isSucceed.observe(this,{
            if(!it){
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

        binding.btnFavUser.setOnClickListener {

//            val values = ContentValues()
//            values.put(DatabaseContract.UserFavoriteColumns.LOGIN, detailUser?.login)
//            values.put(DatabaseContract.UserFavoriteColumns.AVATAR_URL, detailUser?.avatarUrl)
//            values.put(DatabaseContract.UserFavoriteColumns.ID, detailUser?.id)
//
//            val result = favUsersHelper.insert(values)
//
//            if (result > 0) {
//                Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Gagal menambah data $result", Toast.LENGTH_SHORT).show()
//            }
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()

        }



    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
    }

    override fun onDestroy() {
        super.onDestroy()
        favUsersHelper.close()
    }


}