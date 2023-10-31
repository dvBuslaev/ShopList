package com.example.todolist.presentation.Model

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.data.ShopItem

class ShopItemDiffCallBack : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}