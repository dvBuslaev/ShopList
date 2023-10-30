package com.example.todolist.presentation.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {


    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemLongClickListener:((shopItem:ShopItem)->Unit)?=null
    var onItemClickListener:((shopItem:ShopItem)->Unit)?=null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            IS_DISABLED -> R.layout.item_shop_disabled

            IS_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("cannot define viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        viewHolder.view.setOnLongClickListener {
            onItemLongClickListener?.invoke(shopItem)
            true
        }
        viewHolder.view.setOnClickListener() {
            onItemClickListener?.invoke(shopItem)
            true
        }







        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()

 
    }

    override fun onViewRecycled(viewHolder: ShopItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvCount.text = ""
        viewHolder.tvName.setTextColor(
            ContextCompat.getColor(
                viewHolder.view.context,
                android.R.color.white
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) IS_ENABLED else IS_DISABLED
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }



    companion object {
        const val IS_ENABLED = 1
        const val IS_DISABLED = 0
        const val MAX_VIEW_POOL=10
    }
}