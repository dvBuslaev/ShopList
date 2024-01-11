package com.example.todolist.presentation.UI

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.presentation.Model.MainViewModel
import com.example.todolist.presentation.Model.ShopListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("No binding")
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        val addButton = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        addButton.setOnClickListener {
            val intent = ShopItemActivity.setupAddIntent(this)
            startActivity(intent)

        }
    }

    private fun setupRecyclerView() {
        val rvShopList = binding.rvShopList
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_DISABLED, ShopListAdapter.MAX_VIEW_POOL
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_ENABLED, ShopListAdapter.MAX_VIEW_POOL
            )
        }
        setupLongClickListener()
        setupClickListener()
        Log.d("setupRecyclerView", "setupRecyclerView")
        setupSwipeMotion(rvShopList)

    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.setupEditIntent(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupSwipeMotion(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Log.d("onMove", "onMove")
                return false

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                Log.d("onSwiped", "onSwiped")
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        Log.d("onSwiped1", "onSwiped1")
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}


