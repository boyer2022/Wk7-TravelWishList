package com.example.travelwishlist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// Creating interface
interface OnDataChangedListener {
    fun onListItemMoved(from: Int, to: Int) // moving UP/Down
    fun onListItemDeleted(position: Int)
}
                            // Private because nothing else will be accessing this
class OnListItemSwipeListener(private val onDataChangedListener: OnDataChangedListener):
    ItemTouchHelper.SimpleCallback(
    // Drag directions
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,         // To Reorder
        ItemTouchHelper.LEFT                                    // To Delete
) {
    // For Moving Up/Down
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition   // Where in the list?
        val toPosition = target.adapterPosition         // Where was it moved to?
        onDataChangedListener.onListItemMoved(fromPosition, toPosition)
        return true     // Are we going to permit the move?
    }

    // Swiping Left/Right
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            onDataChangedListener.onListItemDeleted(viewHolder.adapterPosition)
        }
    }
}