package com.example.travelwishlist

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    // Private to prevent duplicate placenames
    private val placeNames = mutableListOf<String>("Brainerd", "Custer")

    // function to access the list
    fun getPlaces(): List<String> {
        return placeNames           // Smart cast to convert mutable list to list
    }

}