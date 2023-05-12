package com.example.submission1.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.ui.viewmodels.MainViewModel
import com.example.submission1.R
import com.example.submission1.adapter.UserAdapter
import com.example.submission1.database.SettingPreferences
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.model.ItemsItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>{
        MainViewModel.factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        var layoutManager = LinearLayoutManager(this)
        binding.listUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUser.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()){
                    query.let {
//                        showLoading(true)
                        mainViewModel.searchUser(resources.getString(R.string.username))
                    }
                } else {
                    query.let {
                        showLoading(true)
                        mainViewModel.searchUser(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()){
                    query.let {
//                        showLoading(true)
                        mainViewModel.searchUser(resources.getString(R.string.username))
                    }
                } else {
                    query.let {
                        showLoading(true)
                        mainViewModel.searchUser(it)
                    }
                }
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.setting -> {
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(userList: List<ItemsItem>) {
        val adapter = UserAdapter(userList)
        binding.listUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}