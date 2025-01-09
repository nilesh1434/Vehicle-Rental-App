package com.example.rentalapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.PayOutActivity
import com.example.rentalapp.adapter.CartAdapter
import com.example.rentalapp.databinding.FragmentCartBinding
import com.example.rentalapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var vehicleNames: MutableList<String>
    private lateinit var vehiclePrices: MutableList<String>
    private lateinit var vehicleDescriptions: MutableList<String>
    private lateinit var vehicleImagesUri: MutableList<String>
    private lateinit var vehicleDetails: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.proceedButton.setOnClickListener {
            //get ordered vehicles details before proceeding to checkout
            getOrderVehiclesDetails()

        }
        return binding.root
    }

    private fun getOrderVehiclesDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        val vehicleName = mutableListOf<String>()
        val vehiclePrice = mutableListOf<String>()
        val vehicleImage = mutableListOf<String>()
        val vehicleDescription = mutableListOf<String>()
        val vehicleDetail = mutableListOf<String>()

        //get vehicle quantities
        val vehicleQuantities = cartAdapter.getUpdatedVehiclesQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (vehicleSnapshot in snapshot.children) {
                    //get cartItems to respective list
                    val orderVehicles = vehicleSnapshot.getValue(CartItems::class.java)
                    //add vehicle details in list
                    orderVehicles?.vehicleName?.let { vehicleName.add(it) }
                    orderVehicles?.vehiclePrice?.let { vehiclePrice.add(it) }
                    orderVehicles?.vehicleDescription?.let { vehicleDescription.add(it) }
                    orderVehicles?.vehicleImage?.let { vehicleImage.add(it) }
                    orderVehicles?.vehicleDetail?.let { vehicleDetail.add(it) }
                }
                orderNow(
                    vehicleName,
                    vehiclePrice,
                    vehicleDescription,
                    vehicleImage,
                    vehicleDetail,
                    vehicleQuantities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order could not be processed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun orderNow(
        vehicleName: MutableList<String>,
        vehiclePrice: MutableList<String>,
        vehicleDescription: MutableList<String>,
        vehicleImage: MutableList<String>,
        vehicleDetail: MutableList<String>,
        vehicleQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("VehicleItemName", vehicleName as ArrayList<String>)
            intent.putExtra("VehicleItemPrice", vehiclePrice as ArrayList<String>)
            intent.putExtra("VehicleItemImage", vehicleImage as ArrayList<String>)
            intent.putExtra("VehicleItemDescription", vehicleDescription as ArrayList<String>)
            intent.putExtra("VehicleItemDetail", vehicleDetail as ArrayList<String>)
            intent.putExtra("VehicleItemQuantities", vehicleQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        //database reference to the Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val vehicleReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        //list to store vehicles
        vehicleNames = mutableListOf()
        vehiclePrices = mutableListOf()
        vehicleImagesUri = mutableListOf()
        vehicleDescriptions = mutableListOf()
        vehicleDetails = mutableListOf()
        quantity = mutableListOf()

        //fetch data from database
        vehicleReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (vehicleSnapshot in snapshot.children) {
                    //get cartItems object from child node
                    val cartItems = vehicleSnapshot.getValue(CartItems::class.java)
                    //add cart vehicle details to the list
                    cartItems?.vehicleName?.let { vehicleNames.add(it) }
                    cartItems?.vehiclePrice?.let { vehiclePrices.add(it) }
                    cartItems?.vehicleImage?.let { vehicleImagesUri.add(it) }
                    cartItems?.vehicleDescription?.let { vehicleDescriptions.add(it) }
                    cartItems?.vehicleQuantity?.let { quantity.add(it) }
                    cartItems?.vehicleDetail?.let { vehicleDetails.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    vehicleNames,
                    vehiclePrices,
                    vehicleImagesUri,
                    vehicleDescriptions,
                    quantity,
                    vehicleDetails
                )
                binding.cartRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetched", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

    }
}