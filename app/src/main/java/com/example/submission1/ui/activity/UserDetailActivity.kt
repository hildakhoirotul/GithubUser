package com.example.submission1.ui.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission1.R
import com.example.submission1.ui.viewmodels.UserDetailViewModel
import com.example.submission1.adapter.SectionPagerAdapter
import com.example.submission1.database.Module
import com.example.submission1.databinding.ActivityUserDetailBinding
import com.example.submission1.model.DetailResponse
import com.example.submission1.model.ItemsItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>{
        UserDetailViewModel.Factory(Module(this))
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem
        userDetailViewModel.getUser(userData.login)

        userDetailViewModel.userDetail.observe(this) { detail ->
            setDetailData(detail)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.setTitle(userData.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = userData.login
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        userDetailViewModel.successFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        userDetailViewModel.deleteFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        binding.btnFavorite.setOnClickListener{
            userDetailViewModel.setFavoriteUser(userData)
        }

        userDetailViewModel.findFavoriteUser(userData?.id ?: 0){
            binding.btnFavorite.changeIconColor(R.color.red)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setDetailData(userDetail: DetailResponse) {
        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(userDetail.avatarUrl)
                .into(userAvatar)
            userRepo.text = userDetail.publicRepos.toString()
            userFollowers.text = userDetail.followers.toString()
            userFollowing.text = userDetail.following.toString()
            fullname.text = userDetail.name ?: resources.getString(R.string.noname)
            userCompany.text = userDetail.company ?: resources.getString(R.string.nocompany)
            userLocation.text = userDetail.location ?: resources.getString(R.string.nolocation)
        }

    }

    fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }

}