package com.macreai.githubapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.macreai.githubapp.R
import com.macreai.githubapp.databinding.ActivityDetailBinding
import com.macreai.githubapp.detail.followerfollowing.SectionPagerAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        detailViewModel.setUserDetail(username)
        detailViewModel.detailUser.observe(this, {
                binding.apply {
                    tvName.text = it.login
                    tvUsername.text = it.name
                    followerCount.text = "${it.followers}"
                    followingCount.text = "${it.following}"
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(userPhoto)
                }
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        adapterSectionPager(bundle)


    }

    private fun adapterSectionPager(bundle: Bundle) {
        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) binding.githubLoading.visibility = View.VISIBLE
        else binding.githubLoading.visibility = View.GONE
    }
}