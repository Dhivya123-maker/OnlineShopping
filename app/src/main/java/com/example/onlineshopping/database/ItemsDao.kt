package com.example.onlineshopping.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemsDao {
    @Query("SELECT * FROM items_table ORDER BY Id DESC")
    fun getAll(): LiveData<List<Item>>


    @Query("SELECT * FROM items_table WHERE Id = :id")
    fun getItemById(id: Long): LiveData<Item>

    @Query("SELECT * FROM items_table WHERE name = :name")
    suspend fun getItemByName(name: String): Item


//    @Query("SELECT * FROM items_table ORDER BY Id DESC")
//    fun getAll_one(): LiveData<List<Item_one>>


    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun update(item: Item)

}
