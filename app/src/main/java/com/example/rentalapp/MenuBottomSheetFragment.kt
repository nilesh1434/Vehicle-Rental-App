package com.example.rentalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.adapter.MenuAdapter
import com.example.rentalapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        val menuVehicleName = listOf("Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent")
        val menuItemPrice = listOf("Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000")
        val menuImage = listOf(
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire,
            R.drawable.dzire
        )

        val adapter = MenuAdapter(
            ArrayList(menuVehicleName),
            ArrayList(menuItemPrice),
            ArrayList(menuImage)
        )

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}