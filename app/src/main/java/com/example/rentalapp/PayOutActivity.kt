package com.example.rentalapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalapp.databinding.ActivityPayOutBinding
import com.example.rentalapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var vehicleItemName: ArrayList<String>
    private lateinit var vehicleItemPrice: ArrayList<String>
    private lateinit var vehicleItemImage: ArrayList<String>
    private lateinit var vehicleItemDescription: ArrayList<String>
    private lateinit var vehicleItemDetail: ArrayList<String>
    private lateinit var vehicleItemQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize Firebase and User details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        //set user data
        setUserData()

        //get user details from Firebase
        val intent = intent
        vehicleItemName = intent.getStringArrayListExtra("VehicleItemName") as ArrayList<String>
        vehicleItemPrice = intent.getStringArrayListExtra("VehicleItemPrice") as ArrayList<String>
        vehicleItemImage = intent.getStringArrayListExtra("VehicleItemImage") as ArrayList<String>
        vehicleItemDescription =
            intent.getStringArrayListExtra("VehicleItemDescription") as ArrayList<String>
        vehicleItemDetail = intent.getStringArrayListExtra("VehicleItemDetail") as ArrayList<String>
        vehicleItemQuantities =
            intent.getIntegerArrayListExtra("VehicleItemQuantities") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + "₹"
        binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.PlaceMyOrder.setOnClickListener {
            //get data from textview
            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phone = binding.phone.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }


        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            vehicleItemName,
            vehicleItemPrice,
            vehicleItemImage,
            vehicleItemQuantities,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratulationsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
            removeVehicleFromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {
            Toast.makeText(this, "History Updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeVehicleFromCart() {
        val cartVehiclesReference = databaseReference.child("user").child(userId).child("CartItems")
        cartVehiclesReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until vehicleItemPrice.size) {
            var price = vehicleItemPrice[i]
            val lastChar = price.last()
            val priceIntValue = if (lastChar == '₹') {
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }
            var quantity = vehicleItemQuantities[i]
            totalAmount += priceIntValue * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val names = snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phones = snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            name.setText(names)
                            address.setText(addresses)
                            phone.setText(phones)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}
