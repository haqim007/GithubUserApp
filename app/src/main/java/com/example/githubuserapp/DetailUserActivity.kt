package com.example.githubuserapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapters.DetailUserPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.models.FavUsers
import com.example.githubuserapp.viewModels.DetailUserViewModel
import com.example.githubuserapp.viewModels.FavUsersViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var detailUser: FavUsers? = null
    private var isAlreadyFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.detail_user)

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)
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

                detailUser = FavUsers(
                    githubId = detailUserViewModel.usersDetail.value?.id,
                    avatarUrl = detailUserViewModel.usersDetail.value?.avatarUrl,
                    login = detailUserViewModel.usersDetail.value?.login
                )

                detailUserViewModel.getByUsername(detailUser!!.login!!).observe(this, { it ->
                    isAlreadyFavorite = if(it != null){
                        binding.btnFavUser.setColorFilter(Color.RED)
                        detailUser!!.id = it.id
                        true
                    }else{
                        binding.btnFavUser.setColorFilter(Color.WHITE)
                        false
                    }
                })

            }
        })

        detailUserViewModel.isSucceed.observe(this,{
            if(!it){
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show()
                showLoading(false)
            }
        })

        binding.btnFavUser.setOnClickListener {

             if(isAlreadyFavorite){
                 isAlreadyFavorite = false
                 detailUserViewModel.delete(detailUser as FavUsers)
                 binding.btnFavUser.setColorFilter(Color.WHITE)
                 Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_LONG).show()
             } else {
                 detailUserViewModel.insert(detailUser as FavUsers)
                 Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_LONG).show()
                 binding.btnFavUser.setColorFilter(Color.RED)
                 isAlreadyFavorite = true
             }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = FavUsersViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
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
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}