package com.example.rentalapp.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rentalapp.RecentOrderVehicles
import com.example.rentalapp.adapter.BuyAgainAdapter
import com.example.rentalapp.databinding.FragmentHistoryBinding
import com.example.rentalapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private var listOfOrderVehicle: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        //initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        //initialize Firebase database
        database = FirebaseDatabase.getInstance()
        //retrieve and display user data history
        retrieveBuyHistory()
        //recently bought button click
        binding.recentBuyVehicle.setOnClickListener {
            seeAllRecentBuyVehicles()
        }
        return binding.root
    }

    //function to see all recently bought history
    private fun seeAllRecentBuyVehicles() {
        listOfOrderVehicle.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), RecentOrderVehicles::class.java)
            intent.putExtra("RecentBuyOrderVehicle", ArrayList(listOfOrderVehicle))
            startActivity(intent)
        }
    }

    //function to retrieve vehicles bought recently
    private fun retrieveBuyHistory() {
        binding.recentBuyVehicle.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyVehicleReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyVehicleReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryVehicle = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryVehicle?.let { listOfOrderVehicle.add(it) }
                }
                listOfOrderVehicle.reverse()
                if (listOfOrderVehicle.isNotEmpty()) {
                    //display most recent order details
                    setDataInRecentBuyVehicle()
                    //setup the recyclerview with previous order details
                    setPreviouslyBuyVehicleRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //function to display most recent order details
    private fun setDataInRecentBuyVehicle() {
        binding.recentBuyVehicle.visibility = View.VISIBLE
        val recentOrderVehicle = listOfOrderVehicle.firstOrNull()
        recentOrderVehicle?.let {
            with(binding) {
                buyAgainVehicleName.text = it.vehicleNames?.firstOrNull() ?: ""
                buyAgainVehiclePrice.text = it.vehiclePrices?.firstOrNull() ?: ""
                val image = it.vehicleImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainVehicleImage)

//                listOfOrderVehicle.reverse()
//                if (listOfOrderVehicle.isNotEmpty()) {
//
//                }
            }
        }
    }

    //function to setup the recyclerview with previous order details
    private fun setPreviouslyBuyVehicleRecyclerView() {
        val buyAgainVehicleName = mutableListOf<String>()
        val buyAgainVehiclePrice = mutableListOf<String>()
        val buyAgainVehicleImage = mutableListOf<String>()
        for (i in 1 until listOfOrderVehicle.size) {
            listOfOrderVehicle[i].vehicleNames?.firstOrNull()?.let {
                buyAgainVehicleName.add(it)
                listOfOrderVehicle[i].vehiclePrices?.firstOrNull()?.let {
                    buyAgainVehiclePrice.add(it)
                    listOfOrderVehicle[i].vehicleImages?.firstOrNull()?.let {
                        buyAgainVehicleImage.add(it)
                    }
                }
                val rv = binding.BuyAgainRecyclerView
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdapter = BuyAgainAdapter(
                    buyAgainVehicleName,
                    buyAgainVehiclePrice,
                    buyAgainVehicleImage,
                    requireContext()
                )
                rv.adapter = buyAgainAdapter
            }
        }
    }
}