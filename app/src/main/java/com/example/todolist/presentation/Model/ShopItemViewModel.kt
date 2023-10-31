package com.example.todolist.presentation.Model

import androidx.lifecycle.ViewModel
import com.example.todolist.data.ShopItem
import com.example.todolist.data.ShopListRepositoryImpl

class ShopItemViewModel:ViewModel() {
    private val repository = ShopListRepositoryImpl

    val shopList = repository.getShopList()

    fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }

    fun addShopItem(inputName: String, inputCount: String?) {


        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            val shopItem = ShopItem(name, count, true)
            repository.addShopItem(shopItem)
        }

    }

    fun getShopItem(shopItemId: Int) {
        repository.getShopItem(shopItemId)
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""


    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }

    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) result = false

        if (count <= 0) result = false
        return result
    }
}