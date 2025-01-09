package com.example.rentalapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentalapp.DetailsActivity
import com.example.rentalapp.databinding.MenuItemBinding
import com.example.rentalapp.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuVehicle = menuItems[position]
            //intent to open details activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuVehicleName", menuVehicle.vehicleName)
                putExtra("MenuVehiclePrice", menuVehicle.vehiclePrice)
                putExtra("MenuVehicleImage", menuVehicle.vehicleImage)
                putExtra("MenuVehicleDescription", menuVehicle.vehicleDescription)
                putExtra("MenuVehicleInfo", menuVehicle.vehicleDetails)
            }
            //start details activity
            requireContext.startActivity(intent)
        }


        //set data in recyclerview items name, price, image
        fun bind(position: Int) {
            val menuVehicle = menuItems[position]
            binding.apply {
                menuCarName.text = menuVehicle.vehicleName
                menuPrice.text = menuVehicle.vehiclePrice
                val uri = Uri.parse(menuVehicle.vehicleImage)
                Glide.with(requireContext).load(uri).into(menuImage)
            }
        }
    }
}


