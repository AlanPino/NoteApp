package com.joeldev.noteapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoldersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvFolder: TextView = view.findViewById(R.id.tvFolder)
    private val imgFolder: ImageView = view.findViewById(R.id.imgFolder)

    fun render(name: String) {
        tvFolder.text = name
    }
}