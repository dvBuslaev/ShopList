package com.example.todolist.presentation.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_ADD = "add_mode"
        private const val SHOP_ITEM = "shop_item_id"

        fun setupEditIntent(context: Context, shopItemID: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(SHOP_ITEM, shopItemID)

            return intent
        }

        fun setupAddIntent(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)

            return intent

        }
    }

}