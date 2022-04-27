package com.mendelin.tmdb_hilt.ui.custom_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemVerticalDecoration(private val horizontal: Int, private val vertical: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = vertical
            }

            left = horizontal
            right = horizontal

            bottom = vertical
        }
    }
}