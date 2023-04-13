package com.example.travelwishlist

import java.text.SimpleDateFormat
import java.util.*

// Constructor()
class Place(val name: String, val reason: String, private val dateAdded: Date = Date() ) {
    fun formattedDate(): String {
        // Date formatter patterns:  https://docs.oracle.com/javase/10/docs/api/java/text/SimpleDateFormat.html
        return SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(dateAdded)
    }

    // Formatted Log string to indicate readable placements of Places
    override fun toString(): String {
        return "$name $reason ${formattedDate()}"
    }
}