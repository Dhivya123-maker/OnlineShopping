package com.example.onlineshopping.itemsList

import androidx.lifecycle.*
import com.example.onlineshopping.database.Item
import com.example.onlineshopping.database.ItemsDao


import kotlinx.coroutines.launch


class ItemsListViewModel(private val dao: ItemsDao) : ViewModel() {

    val items: LiveData<List<Item>> = dao.getAll()

    val countItems: LiveData<Int> = Transformations.map(items) { it.size }


    fun removeItem(item: Item) {
        viewModelScope.launch { dao.delete(item) }

    }

    fun incrementQuantityForItem(item: Item) {
        viewModelScope.launch {
            with(item.copy()) {
                this.incrementQuantity()
                dao.update(this)
            }
        }

    }

    fun decrementQuantityForItem(item: Item) {
        if (item.quantity == 0) return
        viewModelScope.launch {
            with(item.copy()) {
                this.decrementQuantity()
                if (this.quantity == 0)
                    removeItem(item)
                    //this.markDone()
                dao.update(this)
            }
        }

    }




    fun addItem(newItem: Item) {
        val itemName = newItem.name
        val itemPrice = newItem.price
        val itemImage = newItem.image

        val newItem = Item(itemName, 1,itemPrice,itemImage)
        viewModelScope.launch { dao.insert(newItem) }

    }





}