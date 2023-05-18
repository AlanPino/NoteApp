package com.joeldev.noteapp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter(
    var items: MutableList<String>,
    var selected: MutableList<Int>,
    private val listener: OnItemRemovedListener,
) : RecyclerView.Adapter<ItemListViewHolder>() {

    interface OnItemRemovedListener {
        fun onItemRemoved(position: Int)
    }

    private lateinit var etItem: EditText

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemListViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.render(items[position], selected[position])

        val btnDelete = holder.itemView.findViewById<ImageButton>(R.id.ibtnDelete)
        etItem = holder.itemView.findViewById(R.id.etItem)
        val cbItem = holder.itemView.findViewById<CheckBox>(R.id.cbItem)


        etItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                items[holder.adapterPosition] = s.toString()
            }
        }
        )

        btnDelete.setOnClickListener {
            listener.onItemRemoved(position)
            notifyDataSetChanged()
        }

        cbItem.setOnClickListener {
            if(selected[position] == 0){
                selected[position] = 1
            }
            else{
                selected[position] = 0
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

