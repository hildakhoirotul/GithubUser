package com.example.submission1.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
): Parcelable

@Parcelize
@Entity(tableName = "user")
data class ItemsItem(

	@PrimaryKey
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int,

	@ColumnInfo(name = "username")
	@field:SerializedName("login")
	val login: String,

	@ColumnInfo(name = "photo")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

): Parcelable
