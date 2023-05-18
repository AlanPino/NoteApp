package com.joeldev.noteapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joeldev.noteapp.NoteApp.Companion.prefs
import kotlin.properties.Delegates

class EditListActivity : AppCompatActivity(), ItemListAdapter.OnItemRemovedListener {
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var rvItemList: RecyclerView
    private lateinit var etTitle: EditText
    private lateinit var items: MutableList<String>
    private lateinit var selected: MutableList<Int>
    private lateinit var btnAddItem: Button
    private var position by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(prefs.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)

        val title = intent.getStringExtra("title").orEmpty()
        items = intent.getStringArrayListExtra("items")?.toMutableList() ?: mutableListOf("")
        selected = intent.getIntegerArrayListExtra("selected")?.toMutableList() ?: mutableListOf(0)
        position = intent.getIntExtra("position", -1)

        initComp()
        initUI()
        initListeners()

        etTitle.setText(title)

    }

    override fun onItemRemoved(position: Int) {
        etTitle.requestFocus()
        etTitle.clearFocus()
        items.removeAt(position)
        itemListAdapter.notifyItemRemoved(position)
    }

    private fun initListeners() {
        btnAddItem.setOnClickListener {
            items.add(items.size, "")
            selected.add(selected.size, 0)
            itemListAdapter.notifyItemInserted(items.size - 1)

            rvItemList.post {
                rvItemList.scrollToPosition(items.size - 1)
                val newItemViewHolder = rvItemList.findViewHolderForAdapterPosition(items.size - 1) as? ItemListViewHolder
                newItemViewHolder?.etItem?.requestFocus()

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(newItemViewHolder?.etItem, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    private fun initUI() {
        itemListAdapter = ItemListAdapter(items, selected,this)
        rvItemList.layoutManager = LinearLayoutManager(this)
        rvItemList.adapter = itemListAdapter
    }

    private fun initComp() {
        etTitle = findViewById(R.id.etTitle)
        rvItemList = findViewById(R.id.rvItems)
        btnAddItem = findViewById(R.id.btnAddItem)
    }

    override fun onBackPressed() {
        returnIntent()
    }


    private fun returnIntent() {
        val intent = Intent()
        intent.putExtra("title", etTitle.text.toString())
        intent.putStringArrayListExtra("items", ArrayList(items))
        intent.putIntegerArrayListExtra("selected", ArrayList(selected))
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent)
        finish()
    }
}