package com.example.submission1.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.database.Module
import com.example.submission1.model.ItemsItem

class FavoriteViewModel(private val dbModule: Module) : ViewModel() {

    fun getFavoriteUser() = dbModule.dao.loadAll()

    class Factory(private val db: Module) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}
