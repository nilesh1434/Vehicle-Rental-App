package com.example.rentalapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentalapp.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context: Context,
    private var vehicleNameList: ArrayList<String>,
    private var vehiclePriceList: ArrayList<String>,
    private var vehicleImageList: ArrayList<String>,
    private var vehicleQuantityList: ArrayList<Int>
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = vehicleNameList.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RecentViewHolder(private val binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                vehicleName.text = vehicleNameList[position]
                vehiclePrice.text = vehiclePriceList[position]
                vehicleQuantity.text = vehicleQuantityList[position].toString()
                val uriString = vehicleImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(vehicleImage)
            }
        }

    }

}