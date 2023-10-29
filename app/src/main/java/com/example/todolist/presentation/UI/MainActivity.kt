package com.example.todolist.presentation.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.presentation.Model.MainViewModel
import com.example.todolist.presentation.Model.ShopListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            shopListAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            adapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_DISABLED,
                ShopListAdapter.MAX_VIEW_POOL
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_ENABLED,
                ShopListAdapter.MAX_VIEW_POOL
            )
        }
    }
}