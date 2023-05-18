package com.joeldev.noteapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ListsAdapter(
    var lists: MutableList<List>,
    private val context: Context,
    private val listener: OnListItemRemovedListener,
    private val onListSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<ListsViewHolder>() {

    interface OnListItemRemovedListener {
        fun onListItemRemoved(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_card, parent, false)
        return ListsViewHolder(view)
    }

    override fun getItemCount() = lists.size

    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        holder.render(lists[position].title)

        holder.itemView.setOnClickListener {
            onListSelected(position)
        }

        holder.itemView.findViewById<ImageButton>(R.id.ibtnMenu).setOnClickListener {
            val popupMenu = PopupMenu(context, holder.itemView.findViewById<ImageButton>(R.id.ibtnMenu))

            popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "renombrar")
            popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "eliminar")

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    1 -> {
                        val dialog = Dialog(context)
                        dialog.setContentView(R.layout.dialog_folder)

                        val btnCreateFolder: Button = dialog.findViewById(R.id.btnCreateFolder)
                        val etFolder: EditText = dialog.findViewById(R.id.etFolder)
                        dialog.findViewById<Button>(R.id.btnCreateFolder).text = "renombrar"
                        dialog.show()

                        btnCreateFolder.setOnClickListener {
                            val title: String = etFolder.text.toString()

                            if (title.isNotEmpty()) {
                                if (lists.any { lists -> lists.title == title}) {
                                    Toast.makeText(context, "este nombre ya existe", Toast.LENGTH_SHORT).show()
                                } else {
                                    lists[position].title = title
                                    notifyDataSetChanged()
                                    dialog.hide()
                                }
                            }
                        }
                        true
                    }
                    2 -> {
                        listener.onListItemRemoved(position)
                        notifyDataSetChanged()

                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

}