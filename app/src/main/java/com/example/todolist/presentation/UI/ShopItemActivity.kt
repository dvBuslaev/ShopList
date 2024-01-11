package com.example.todolist.presentation.UI

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.data.ShopItem
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.ActivityShopItemBinding
import com.example.todolist.presentation.Model.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModel

    private var _binding: ActivityShopItemBinding? = null
    private val binding: ActivityShopItemBinding
        get() = _binding ?: throw RuntimeException("No binding")

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        when (screenMode) {
            MODE_ADD -> lounchAddMode()
            MODE_EDIT -> lounchEditMode()
        }
        setupTextListeners()
        observeViewModel()


    }

    private fun observeViewModel() {

        viewModel.errorInputCount.observe(this) {
            val massage = if (it) {
                getString(R.string.error_input_count)
            } else {
                null

            }
            binding.tilCount.error = massage
        }
        viewModel.errorInputName.observe(this) {
            val massage = if (it) {
                getString(R.string.error_input_name)
            } else {
                null

            }
            binding.tilName.error = massage
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun setupTextListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun lounchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun lounchAddMode() {

        binding.saveButton.setOnClickListener {
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()
            viewModel.addShopItem(name, count)
        }

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_ADD = "add_mode"
        private const val SHOP_ITEM = "shop_item_id"
        private const val MODE_UNKNOWN = ""

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

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(SHOP_ITEM)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(SHOP_ITEM, ShopItem.UNDEFINED_ID)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}