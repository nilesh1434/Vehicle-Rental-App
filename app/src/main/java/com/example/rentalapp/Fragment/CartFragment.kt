package com.example.rentalapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.R
import com.example.rentalapp.adapter.CartAdapter
import com.example.rentalapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartVehicleName = listOf("Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent")
        val cartItemPrice = listOf("Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000")
        val cartImage = listOf(
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire
        )
        val adapter = CartAdapter(ArrayList(cartVehicleName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}