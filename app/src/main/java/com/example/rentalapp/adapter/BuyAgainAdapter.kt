package com.example.rentalapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentalapp.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainVehicleName: MutableList<String>,
    private val buyAgainVehiclePrice: MutableList<String>,
    private val buyAgainVehicleImage: MutableList<String>,
    private var requireContext: Context
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(
            buyAgainVehicleName[position],
            buyAgainVehiclePrice[position],
            buyAgainVehicleImage[position]
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainVehicleName.size

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicleName: String, vehiclePrice: String, vehicleImage: String) {
            binding.buyAgainVehicleName.text = vehicleName
            binding.buyAgainVehiclePrice.text = vehiclePrice
            val uriString = vehicleImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainVehicleImage)
        }

    }
}