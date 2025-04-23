package com.example.mywiki

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(item:Item)
    @Delete
   suspend fun delete(item:Item)
    @Query("Select * from item_table order by id ASC")
    fun getAllItems():LiveData<List<Item>>
}