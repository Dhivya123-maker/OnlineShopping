package com.example.onlineshopping.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Item(
    @ColumnInfo val name: String,
    @ColumnInfo var quantity: Int = 1,
    @ColumnInfo var price: String? = null,
    @ColumnInfo(name = "image", defaultValue = "null")
    var image: String? = null,
    @ColumnInfo var description: String? = null,
    @ColumnInfo var complete: Boolean = false,
    @PrimaryKey(autoGenerate = true) val Id: Long = 0L
) {
    fun incrementQuantity(): Int = this.quantity++

    fun decrementQuantity(): Int = this.quantity--


}
