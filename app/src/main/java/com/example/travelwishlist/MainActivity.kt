package com.example.travelwishlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

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
        val places = placesViewModel.getPlaces()

        // Setting up the RecyclerView. Data is from places list in PlacesRecyclerAdapter.kt
        placesRecyclerAdapter = PlaceRecyclerAdapter(places)
        // View Component
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

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
            // Modifies the placesViewModel with new data
            val positionAdded = placesViewModel.addNewPlace(name)

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
}