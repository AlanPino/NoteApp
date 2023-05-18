package com.joeldev.noteapp

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class ItemListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val etItem: EditText = view.findViewById(R.id.etItem)
    val btnDelete: ImageButton = view.findViewById(R.id.ibtnDelete)
    var cbItem : CheckBox = view.findViewById(R.id.cbItem)

    fun render(text: String, isSelected: Int) {
        etItem.setText(text)

        cbItem.isChecked = isSelected != 0
    }
}