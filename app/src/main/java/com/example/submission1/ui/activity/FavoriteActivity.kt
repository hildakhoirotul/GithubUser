package com.example.submission1.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.adapter.UserAdapter
import com.example.submission1.database.Module
import com.example.submission1.databinding.ActivityFavoriteBinding
import com.example.submission1.model.ItemsItem
import com.example.submission1.ui.viewmodels.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(Module(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.favoriteUser.layoutManager = LinearLayoutManager(this)

        viewModel.getFavoriteUser().observe(this) {
            setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setData(userList: List<ItemsItem>) {
        val adapter = UserAdapter(userList)
        binding.favoriteUser.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}