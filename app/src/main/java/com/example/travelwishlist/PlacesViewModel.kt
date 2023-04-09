package com.example.travelwishlist

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    // Private to prevent duplicate placenames
    private val places = mutableListOf<Place>(
        Place("Brainerd, MN"), Place("Custer, SD"), Place("Minneapolis, MN"),
          Place("Mystic, SD"), Place( "Melbourne, AU"))

    // function to access the list
    fun getPlaces(): List<Place> {
        return places           // Smart cast to convert mutable list to list
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {         // Nullable Integer = ?

        // Avoid duplicates
/*
    // Duplicate loop check
        for (placeName in placeNames) {
            if (placeName.name.uppercase() == place.name.uppercase()) {
                return - 1          // -1 indicates no position, position not found
            }
        }
 */
        // Kotlin list function "any" or "all"
            // 'all' returns true if all of the list items meet this condition
            // 'any' returns true if any of the placeName list items meet this condition
        if(places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() } ) {
            return -1       // Indicates 'not added'
        }
        // Implement add at position
        return if (position == null) {      // If no position is given
            // return location in the list that the new item was added
            places.add(place)       // Basic version adds to the end of the list
            places.lastIndex
        } else {
            places.add(position, place)
            position
        }



    }

    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        // Log statement to verify move
        Log.d(TAG, places.toString())
    }
}