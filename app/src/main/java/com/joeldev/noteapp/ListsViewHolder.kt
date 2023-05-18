package com.joeldev.noteapp

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvList: TextView = view.findViewById(R.id.tvNote)

    fun render(title: String) {
        tvList.text = title
    }
}