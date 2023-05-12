package com.example.submission1.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.submission1.api.ApiConfig
import com.example.submission1.database.Module
import com.example.submission1.model.DetailResponse
import com.example.submission1.model.ItemsItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(private val db: Module) : ViewModel() {
    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val successFavorite = MutableLiveData<Boolean>()
    val deleteFavorite = MutableLiveData<Boolean>()
    private var isFavorite = false

    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    internal fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setFavoriteUser(item: ItemsItem?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.dao.delete(item)
                    deleteFavorite.value = true
                } else {
                    db.dao.insert(item)
                    successFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavoriteUser(id: Int, listenFavorite: ()-> Unit) {
        viewModelScope.launch {
            val user = db.dao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    class Factory(private val db: Module) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = UserDetailViewModel(db) as T
    }
}
