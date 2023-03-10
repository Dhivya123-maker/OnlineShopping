package com.example.onlineshopping.itemsList

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.database.Item
import com.example.onlineshopping.databinding.ItemBinding

import com.squareup.picasso.Picasso

class ItemsAdapter(

    private val itemPlusButtonClickListener: (item: Item) -> Unit,
    private val itemMinusButtonClickListener: (item: Item) -> Unit,
) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(DiffItemCallBack()) {
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.dataBind(
            item,
            itemPlusButtonClickListener,
            itemMinusButtonClickListener,
        )
    }



    class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }


        }

        fun dataBind(
            item: Item,
            itemPlusButtonClickListener: (item: Item) -> Unit,
            itemMinusButtonClickListener: (item: Item) -> Unit,
        ) {
            binding.item = item
            binding.plusButton.setOnClickListener { itemPlusButtonClickListener(item) }
            binding.minusButton.setOnClickListener { itemMinusButtonClickListener(item) }
            Picasso.get().load(item.image).into(binding.imageView)


        }
    }
}
