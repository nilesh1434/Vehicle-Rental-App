package com.example.rentalapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rentalapp.databinding.FragmentCongratulationsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratulationsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCongratulationsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentCongratulationsBottomSheetBinding.inflate(layoutInflater, container, false)
        binding.goHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

    }
}