package com.example.submission1.database

import android.content.Context
import androidx.room.Room

class Module (private val context: Context) {
    private val db = Room.databaseBuilder(context, AppDb::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val dao = db.dao()
}