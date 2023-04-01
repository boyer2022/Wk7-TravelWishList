/* This is the Adapter */

package com.example.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Constructor
class PlaceRecyclerAdapter(private val places: List<String>):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // Manages one view - one list item- sets the actual data in the view
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(place: String) {
            val placeNameTextView = view.findViewById<TextView>(R.id.place_name)
            placeNameTextView.text = place
        }
    }
    // Create a ViewHolder for a specific position? (combo view + data
        // called by the recycler view to create as many view holders that are needed to display
        // the list on screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_list_item, parent, false)
        return ViewHolder(view)
    }

    // Bind the ViewHolder with data for a specific position?
        // called by the recycler view to set the data for on list item, by position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    // How many items in the list?
        // What is the total items in the list
    override fun getItemCount(): Int {
        return places.size
    }
}