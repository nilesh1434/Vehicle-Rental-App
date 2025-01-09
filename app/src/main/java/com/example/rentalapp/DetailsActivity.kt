package com.example.rentalapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.rentalapp.databinding.ActivityDetailsBinding
import com.example.rentalapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var vehicleName: String? = null
    private var vehiclePrice: String? = null
    private var vehicleImage: String? = null
    private var vehicleDescription: String? = null
    private var vehicleInfo: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        vehicleName = intent.getStringExtra("MenuVehicleName")
        vehiclePrice = intent.getStringExtra("MenuVehiclePrice")
        vehicleDescription = intent.getStringExtra("MenuVehicleDescription")
        vehicleInfo = intent.getStringExtra("MenuVehicleInfo")
        vehicleImage = intent.getStringExtra("MenuVehicleImage")

        with(binding) {
            detailVehicleName.text = vehicleName
            detailDescription.text = vehicleDescription
            detailInfo.text = vehicleInfo
            Glide.with(this@DetailsActivity).load(Uri.parse(vehicleImage)).into(detailVehicleImage)
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.addVehicleButton.setOnClickListener {
            addVehicleToCart()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addVehicleToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        //create a cartItems object
        val cartItem = CartItems(
            vehicleName.toString(),
            vehiclePrice.toString(),
            vehicleImage.toString(),
            vehicleDescription.toString(),
            vehicleQuantity = 1
        )

        //save data to cart item to Firebase database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Vehicle added to cart successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
            Toast.makeText(this, "Vehicle not added", Toast.LENGTH_SHORT).show()
        }
    }
}