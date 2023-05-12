package com.example.submission1.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import com.example.submission1.model.ItemsItem

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ItemsItem)

    @Query("SELECT * FROM user")
    fun loadAll(): LiveData<MutableList<ItemsItem>>

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ItemsItem

    @Delete
    fun delete(user: ItemsItem)
}