package com.example.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapters.ListUsersAdapter
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.helpers.data.SettingPreferences
import com.example.githubuserapp.models.UsersResponseItem
import com.example.githubuserapp.viewModels.MainViewModel
import com.example.githubuserapp.viewModels.ViewModelFactory
import kotlin.properties.Delegates

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var list: MutableList<UsersResponseItem?>? = null
    private var isNightMode: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = resources.getString(R.string.github_users)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvUsers.setHasFixedSize(true)
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[MainViewModel::class.java]

        showLoading(true)
        mainViewModel.getUsers()
        mainViewModel.usersList.observe(this, {
            list?.clear()
            list = it as MutableList<UsersResponseItem?>?
            showRecyclerList()
            showLoading(false)
        })
        mainViewModel.isSucceed.observe(this,{
            if(!it){
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                isNightMode = isDarkModeActive
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView?

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.queryHint = resources.getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                mainViewModel.getSearchUsers(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                showLoading(true)
                if (newText == ""){
                    mainViewModel.getUsers()
                }else{
                    mainViewModel.getSearchUsers(newText)
                }

                return true
            }
        })


        val switcher = menu.findItem(R.id.nightMode)
        switcher.setActionView(R.layout.switch_layout)
        val nightModeSwitcher: SwitchCompat = switcher.actionView.findViewById(R.id.scNightMode)
        nightModeSwitcher.isChecked = isNightMode == true

        nightModeSwitcher.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
            nightModeSwitcher.isChecked = isChecked
        }

        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUsersAdapter = ListUsersAdapter(list,::openDetail)
        binding.rvUsers.adapter = listUsersAdapter
    }

    private fun openDetail(username: String){
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }

}