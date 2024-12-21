package com.example.rentalapp.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.R
import com.example.rentalapp.adapter.MenuAdapter
import com.example.rentalapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : MenuAdapter
    private val originalMenuVehicleName = listOf("Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent", "Swift Dzire", "WagonR", "Accent")
    private val originalMenuItemPrice = listOf("Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000", "Rs 5000", "Rs 3000", "Rs 6000")
    private val originalMenuImage = listOf(
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val filteredMenuVehicleName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(filteredMenuVehicleName, filteredMenuItemPrice,filteredMenuImage)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        //setup for search view
        setupSearchView()

        //show all menu items
        showAllMenu()

        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuVehicleName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuVehicleName.addAll(originalMenuVehicleName)
        filteredMenuItemPrice.addAll(originalMenuItemPrice)
        filteredMenuImage.addAll(originalMenuImage)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        filteredMenuVehicleName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        originalMenuVehicleName.forEachIndexed { index, vehicleName ->
            if (vehicleName.contains(query, ignoreCase = true)){
                filteredMenuVehicleName.add(vehicleName)
                filteredMenuItemPrice.add(originalMenuItemPrice[index])
                filteredMenuImage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}