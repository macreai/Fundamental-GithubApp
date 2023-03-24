package com.macreai.githubapp.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.macreai.githubapp.R
import com.macreai.githubapp.UserAdapter
import com.macreai.githubapp.api_configuration.ItemsItem
import com.macreai.githubapp.databinding.ActivityMainBinding
import com.macreai.githubapp.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this, {
            showRecyclerView(it)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.clearFocus()
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUsers(query)
                Toast.makeText(
                    this@MainActivity,
                    "Pencarian untuk $query",
                    Toast.LENGTH_SHORT)
                    .show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) mainViewModel.findUsers("arda")
                return true
            }

        })
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerView(itemsItems: List<ItemsItem>) {

        adapter = UserAdapter(itemsItems)
        adapter.notifyDataSetChanged()
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

        }

        //item click
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                startActivity(intentToDetail)
            }

        })
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) binding.githubLoading.visibility = View.VISIBLE
        else binding.githubLoading.visibility = View.GONE
    }

}