package com.example.submission1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.ui.activity.UserDetailActivity
import com.example.submission1.databinding.ItemUserBinding
import com.example.submission1.model.ItemsItem

class UserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        val userData = listUser[position]
        viewHolder.binding.tvItemName.text = userData.login
        Glide.with(viewHolder.itemView.context)
            .load(userData.avatarUrl)
            .into(viewHolder.binding.imgItemPhoto)

        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, UserDetailActivity::class.java).apply {
                putExtra("extra_user", userData)
            }
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }
}