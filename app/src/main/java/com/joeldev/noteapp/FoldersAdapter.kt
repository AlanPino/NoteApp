package com.joeldev.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FoldersAdapter(
    private val names: MutableList<String>,
    private val onFolderSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<FoldersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_card, parent, false)
        return FoldersViewHolder(view)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: FoldersViewHolder, position: Int) {
        holder.render(names[position])

        holder.itemView.setOnClickListener {
            onFolderSelected(position)
        }
    }

}