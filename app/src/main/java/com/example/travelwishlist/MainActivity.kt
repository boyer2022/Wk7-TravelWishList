package com.example.travelwishlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.net.URI

class MainActivity : AppCompatActivity(),OnListItemClickedListener, OnDataChangedListener {

    private lateinit var newPlaceEditText: EditText
    private lateinit var addNewPlaceButton: Button
    // RecyclerView
    private lateinit var placeListRecyclerView: RecyclerView
    // Reference to the RecyclerView
    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter
    // Reference to the ViewModel, only going to get used when call - lazy
    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        newPlaceEditText = findViewById(R.id.new_place_name)

        // Asking ViewModel for a list of places
        val places = placesViewModel.getPlaces()        // List of place objects

        // Setting up the RecyclerView. Data is from places list in PlacesRecyclerAdapter.kt
        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        // View Component
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        val itemSwipeListener = OnListItemSwipeListener(this)
        ItemTouchHelper(itemSwipeListener).attachToRecyclerView(placeListRecyclerView)

        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }
    }

    private fun addNewPlace() {
        // Reads the data from the editText and adding it to the list
        val name = newPlaceEditText.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a place name", Toast.LENGTH_SHORT).show()
        } else {
            val newPlace = Place(name)
            // Modifies the placesViewModel with new data
            val positionAdded = placesViewModel.addNewPlace(newPlace)

// Checking if position was added in PlacesViewModel.kt
            if (positionAdded == -1) {
                Toast.makeText(this, "You already added that place.", Toast.LENGTH_SHORT).show()
            } else {
            // Telling the RecyclerAdapter the list has changed
                placesRecyclerAdapter.notifyItemInserted(positionAdded)
                // Calling Clear Form function to clear all the text in the textBox
                clearForm()
                // Function that makes the keyboard go away to prevent typing
                hideKeyBoard()
            }
        }
    }

    private fun clearForm() {
        newPlaceEditText.text.clear()
    }

    private fun hideKeyBoard() {
        this.currentFocus?.let { view ->            // Current focus = what is the user typing in?
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        // imm = "Input Method Manager"
           imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onListItemClicked(place: Place) {
        Toast.makeText(this, "${place.name} map icon was clicked",
            // Alternative: add as milliseconds
            // 5000             // Uncomment to use
            Toast.LENGTH_SHORT).show()      // Comment out this line to test milliseconds version
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onListItemMoved(from: Int, to: Int) {
        // Call to placesViewModel
        placesViewModel.movePlace(from, to)
        // Tell adapter the data source has changed
        placesRecyclerAdapter.notifyItemMoved(from, to)
    }

    override fun onListItemDeleted(position: Int) {
        val deletedPLace = placesViewModel.deletePLace(position)
        placesRecyclerAdapter.notifyItemRemoved(position)

        Snackbar.make(findViewById(R.id.wishlist_container),
            "${deletedPLace.name} deleted", Snackbar.LENGTH_LONG)
            .setActionTextColor(resources.getColor(R.color.warning_red))
            .setBackgroundTint(resources.getColor(R.color.dark_grey))
            .setAction(getString(R.string.undo)) {          // Action to display an "UNDO"
                placesViewModel.addNewPlace(deletedPLace,position)
       // Since the Recycler has been informed of the removal of a "Place",
                // we have to inform the Recycler to insert a "PLace" by position
                placesRecyclerAdapter.notifyItemInserted(position)
            }
            .show()

    }
}