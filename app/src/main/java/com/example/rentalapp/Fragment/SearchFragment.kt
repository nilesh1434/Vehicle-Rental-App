package com.example.rentalapp.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalapp.adapter.MenuAdapter
import com.example.rentalapp.databinding.FragmentSearchBinding
import com.example.rentalapp.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuVehicles = mutableListOf<MenuItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //retrieve menu vehicles from database
        retrieveMenuVehicle()
        //setup for search view
        setupSearchView()

        return binding.root
    }

    private fun retrieveMenuVehicle() {
        //get database reference
        database = FirebaseDatabase.getInstance()
        //reference to the menu node
        val vehicleReference: DatabaseReference = database.reference.child("menu")
        vehicleReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (vehicleSnapshot in snapshot.children) {
                    val menuVehicle = vehicleSnapshot.getValue(MenuItem::class.java)
                    menuVehicle?.let {
                        originalMenuVehicles.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuVehicle = ArrayList(originalMenuVehicles)
        setAdapter(filteredMenuVehicle)
    }

    private fun setAdapter(filteredMenuVehicle: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuVehicle, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
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
        val filteredMenuVehicles = originalMenuVehicles.filter {
            it.vehicleName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuVehicles)
    }

    companion object {

    }
}