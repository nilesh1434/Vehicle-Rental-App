package com.example.rentalapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalapp.databinding.MenuItemBinding

class MenuAdapter(private val menuItemsName:MutableList<String>, private val menuItemPrice:MutableList<String>, private val MenuImage:MutableList<Int>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =menuItemsName.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                menuCarName.text = menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(MenuImage[position])
            }
        }

    }
}