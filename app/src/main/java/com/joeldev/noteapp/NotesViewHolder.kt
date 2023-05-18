package com.joeldev.noteapp

import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView

class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val tvNote: TextView = view.findViewById(R.id.tvNote)

    fun render(title: String){
        tvNote.text = title

    }

}