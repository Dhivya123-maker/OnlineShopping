package com.example.onlineshopping.itemsList

import androidx.recyclerview.widget.DiffUtil
import com.example.onlineshopping.database.Item

class DiffItemCallBack: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val result = oldItem == newItem
        return result
    }
}