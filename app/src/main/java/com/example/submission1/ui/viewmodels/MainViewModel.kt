package com.example.submission1.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.submission1.api.ApiConfig
import com.example.submission1.database.SettingPreferences
import com.example.submission1.model.GithubResponse
import com.example.submission1.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "arif"
    }

    init {
        findUser()
    }

    fun getTheme() = preferences.getSettingTheme().asLiveData()

    internal fun searchUser(searchUser: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(searchUser)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })


    }

    class factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}