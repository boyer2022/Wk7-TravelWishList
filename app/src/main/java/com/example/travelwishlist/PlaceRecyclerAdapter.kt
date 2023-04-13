/* This is the Adapter */

package com.example.travelwishlist

import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Interface
interface OnListItemClickedListener {
    fun onListItemClicked(place: Place)
}

// Constructor
class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // Manages one view - one list item- sets the actual data in the view
        // Nested classes
        // Inner classes - can access data
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(place: Place) {
            // setting the name of the Place
            val placeNameTextView = view.findViewById<TextView>(R.id.place_name)
            val placeWhyVisitTextView = view.findViewById<TextView>(R.id.reason_why_list)
            val createdOnText = view.context.getString(R.string.created_on, place.formattedDate())
            placeNameTextView.text = place.name
            placeWhyVisitTextView.text = place.reason

            // Setting the date of the Place
            val dateCreatedOnTextView: TextView = view.findViewById(R.id.date_place_added)
            dateCreatedOnTextView.text = createdOnText

            // Finding the map icon and setting it's listener
            val mapIcon: ImageView = view.findViewById(R.id.map_icon)
            mapIcon.setOnClickListener {
                onListItemClickedListener.onListItemClicked(place)
            }
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