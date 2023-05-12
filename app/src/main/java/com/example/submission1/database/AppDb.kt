package com.example.submission1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submission1.model.ItemsItem

@Database(entities = [ItemsItem::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun dao(): Dao
}