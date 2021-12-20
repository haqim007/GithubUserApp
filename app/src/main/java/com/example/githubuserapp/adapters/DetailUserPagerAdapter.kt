package com.example.githubuserapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.ListFollowersFragment
import com.example.githubuserapp.ListFollowingFragment

class DetailUserPagerAdapter(activity: AppCompatActivity, val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = ListFollowersFragment.newInstance(username)
            1 -> fragment = ListFollowingFragment.newInstance(username)
        }

        return fragment as Fragment
    }
}