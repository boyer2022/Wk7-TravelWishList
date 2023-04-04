package com.example.travelwishlist

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    // Private to prevent duplicate placenames
    private val placeNames = mutableListOf<String>("Brainerd", "Custer", "Minneapolis", "Mystic")

    // function to access the list
    fun getPlaces(): List<String> {
        return placeNames           // Smart cast to convert mutable list to list
    }

    fun addNewPlace(place: String, position: Int? = null): Int {         // Nullable Integer = ?

        // Avoid duplicates
/*
    // Duplicate loop check
        for (placeName in placeNames) {
            if (placeName.uppercase() == place.uppercase()) {
                return - 1          // -1 indicates no position, position not found
            }
        }
 */
        // Kotlin list function "any" or "all"
            // 'all' returns true if all of the list items meet this condition
            // 'any' returns true if any of the placeName list items meet this condition
        if(placeNames.any { placeName -> placeName.uppercase() == place.uppercase() } ) {
            return -1       // Indicates 'not added'
        }
        // Implement add at position
        return if (position == null) {      // If no position is given
            // return location in the list that the new item was added
            placeNames.add(place)       // Basic version adds to the end of the list
            placeNames.lastIndex
        } else {
            placeNames.add(position, place)
            position
        }



    }
}