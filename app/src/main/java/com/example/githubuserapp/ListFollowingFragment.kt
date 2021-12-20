package com.example.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapters.ListUsersAdapter
import com.example.githubuserapp.databinding.FragmentListFollowingBinding
import com.example.githubuserapp.models.UsersResponseItem
import com.example.githubuserapp.viewModels.DetailUserViewModel


class ListFollowingFragment : Fragment() {
    private var _binding: FragmentListFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var viewModel: DetailUserViewModel
    private var list: MutableList<UsersResponseItem?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListFollowingBinding.inflate(inflater, container, false)
        arguments?.let {
            username = it.getString(EXTRA_USERNAME).toString()
        }
        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        showLoading(true)
        viewModel.getFollowing(username)
        viewModel.followingList.observe(viewLifecycleOwner, {
            list?.clear()
            list = it as MutableList<UsersResponseItem?>?
            showRecyclerList()
            showLoading(false)
        })
        viewModel.isSucceed.observe(viewLifecycleOwner,{
            if(!it){
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

        return binding.root
    }

    private fun showRecyclerList() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        val listUsersAdapter = ListUsersAdapter(list,::openDetail)
        binding.rvFollowers.adapter = listUsersAdapter
    }

    private fun openDetail(username: String){
        val intent = Intent(context, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @JvmStatic
        fun newInstance(username: String) =
            ListFollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                }
            }
    }

}