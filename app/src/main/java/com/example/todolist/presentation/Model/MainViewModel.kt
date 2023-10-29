package com.example.todolist.presentation.Model

import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopItem
import com.example.todolist.data.ShopListRepositoryImpl


class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl



    val shopList = repository.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        repository.editShopItem(newItem)
    }
}

