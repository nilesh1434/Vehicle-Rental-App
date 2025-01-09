package com.example.rentalapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.adapter.RecentBuyAdapter
import com.example.rentalapp.databinding.ActivityRecentOrderVehiclesBinding
import com.example.rentalapp.model.OrderDetails

class RecentOrderVehicles : AppCompatActivity() {
    private val binding: ActivityRecentOrderVehiclesBinding by lazy {
        ActivityRecentOrderVehiclesBinding.inflate(layoutInflater)
    }

    private lateinit var allVehicleNames: ArrayList<String>
    private lateinit var allVehiclePrices: ArrayList<String>
    private lateinit var allVehicleImages: ArrayList<String>
    private lateinit var allVehicleQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val recentOrderVehicles =
            intent.getSerializableExtra("RecentBuyOrderVehicle") as ArrayList<OrderDetails>
        recentOrderVehicles?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentOrderVehicle = orderDetails[0]
                allVehicleNames = recentOrderVehicle.vehicleNames as ArrayList<String>
                allVehiclePrices = recentOrderVehicle.vehiclePrices as ArrayList<String>
                allVehicleImages = recentOrderVehicle.vehicleImages as ArrayList<String>
                allVehicleQuantities = recentOrderVehicle.vehicleQuantities as ArrayList<Int>
            }

        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.recyclerViewRecentBuy
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(
            this,
            allVehicleNames,
            allVehiclePrices,
            allVehicleImages,
            allVehicleQuantities
        )
        rv.adapter = adapter
    }
}