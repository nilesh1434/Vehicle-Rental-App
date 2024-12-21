package com.example.rentalapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.rentalapp.MenuBottomSheetFragment
import com.example.rentalapp.R
import com.example.rentalapp.adapter.PopularAdapter
import com.example.rentalapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewAllRides.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.cars, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cars, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cars, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
        val vehicleName = listOf("Swift Dzire", "WagonR", "Accent", "...")
        val Price = listOf("Rs 500", "Rs 800", "Rs 1000", "Rs ...")
        val popularVehicleImages = listOf(R.drawable.dzire, R.drawable.cars, R.drawable.cars, R.drawable.cars)
        val adapter = PopularAdapter(vehicleName, Price, popularVehicleImages)
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    companion object {
    }
}