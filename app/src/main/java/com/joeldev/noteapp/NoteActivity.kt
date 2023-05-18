package com.joeldev.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joeldev.noteapp.NoteApp.Companion.prefs


class NoteActivity : AppCompatActivity(), NotesAdapter.OnNoteItemRemovedListener,
    ListsAdapter.OnListItemRemovedListener {

    private lateinit var rvNotes: RecyclerView
    private lateinit var rvLists: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var listsAdapter: ListsAdapter
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var noteType: PopupMenu
    private lateinit var tvBanner: TextView
    private lateinit var folderName: String
    private lateinit var tvNoteTag: TextView
    private lateinit var tvListTag: TextView
    private lateinit var notes: MutableList<Note>
    private lateinit var lists: MutableList<List>


    private var allNotes = prefs.getNotes()

    private var allLists = prefs.getLists()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(prefs.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        folderName = intent.getStringExtra("folderName")!!
        notes = allNotes.filter { it.folder == folderName }.toMutableList()
        lists = allLists.filter { it.folder == folderName }.toMutableList()

        initComp()
        initUI()
        initListeners()
        initPopupMenu()

        tvNoteTag.isVisible = notes.isNotEmpty()
        tvListTag.isVisible = lists.isNotEmpty()
    }

    override fun onNoteItemRemoved(position: Int) {
        allNotes.remove(notes[position])
        notes.removeAt(position)
        notesAdapter.notifyDataSetChanged()
        tvNoteTag.isVisible = notes.isNotEmpty()

        prefs.saveNotes(allNotes)
    }

    override fun onListItemRemoved(position: Int) {
        allLists.remove(lists[position])
        lists.removeAt(position)
        listsAdapter.notifyDataSetChanged()
        tvListTag.isVisible = lists.isNotEmpty()

        prefs.saveLists(allLists)
    }

    private fun initPopupMenu() {
        noteType = PopupMenu(this, fabAddNote)

        noteType.menu.add(Menu.NONE, 1, Menu.NONE, "nota")
        noteType.menu.add(Menu.NONE, 2, Menu.NONE, "lista")

        noteType.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                1 -> {
                    val intent = Intent(this, EditNoteActivity::class.java)
                    editNoteLauncher.launch(intent)
                    true
                }

                2 -> {
                    val intent = Intent(this, EditListActivity::class.java)
                    editListLauncher.launch(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun initListeners() {
        fabAddNote.setOnClickListener {
            noteType.show()
        }
    }

    private fun initUI() {
        notesAdapter = NotesAdapter(notes, this, this) { onNoteSelected(it) }
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = notesAdapter

        listsAdapter = ListsAdapter(lists, this, this) { onListSelected(it) }
        rvLists.layoutManager = LinearLayoutManager(this)
        rvLists.adapter = listsAdapter

    }

    private fun initComp() {
        fabAddNote = findViewById(R.id.fabAddNote)
        rvNotes = findViewById(R.id.rvNotes)
        rvLists = findViewById(R.id.rvLists)
        tvBanner = findViewById(R.id.tvBanner)
        tvNoteTag = findViewById(R.id.tvNoteTag)
        tvListTag = findViewById(R.id.tvListTag)
    }

    private var editNoteLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            var title = activityResult.data?.getStringExtra("title").orEmpty()
            val text = activityResult.data?.getStringExtra("text").orEmpty()
            val position = activityResult.data!!.getIntExtra("position", -1)

            if (position == -1) {

                if (title.isNotEmpty()) {

                    while (notes.any { note -> note.title == title }) {
                        title += "#"
                    }

                    notes.add(Note(title, text, folderName))
                    allNotes.add(Note(title, text, folderName))

                } else if (text.isNotEmpty()) {
                    notes.add(Note("-sin titulo-", text, folderName))
                    allNotes.add(Note("-sin titulo-", text, folderName))
                }
            } else {
                notes[position].title = ""
                allNotes[allNotes.indexOf(notes[position])].title = ""

                if (title.isNotEmpty()) {

                    while (notes.any { note -> note.title == title }) {
                        title += "#"
                    }

                    notes[position].title = title
                    allNotes[allNotes.indexOf(notes[position])].title = title
                    notes[position].text = text
                    allNotes[allNotes.indexOf(notes[position])].text = text

                } else if (text.isNotEmpty()) {
                    notes[position].title = "-sin titulo-"
                    allNotes[allNotes.indexOf(notes[position])].title = "-sin titulo-"
                    notes[position].text = text
                    allNotes[allNotes.indexOf(notes[position])].text = text
                }

            }
            notesAdapter.notes = notes
            notesAdapter.notifyDataSetChanged()

            tvNoteTag.isVisible = notes.isNotEmpty()
            prefs.saveNotes(allNotes)
        }

    private var editListLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            var title = activityResult.data?.getStringExtra("title").orEmpty()
            val items =
                activityResult.data?.getStringArrayListExtra("items").orEmpty().toMutableList()
            val selected = activityResult.data?.getIntegerArrayListExtra("selected").orEmpty().toMutableList()
            val position = activityResult.data!!.getIntExtra("position", -1)

            if (position == -1) {

                if (title.isNotEmpty()) {

                    while (lists.any { lists -> lists.title == title }) {
                        title += "#"
                    }

                    lists.add(List(title, items, selected,folderName))
                    allLists.add(List(title, items, selected,folderName))

                } else if (items.isNotEmpty()) {
                    lists.add(List("-sin titulo-", items, selected, folderName))
                    allLists.add(List("-sin titulo-", items, selected,folderName))
                }
            } else {
                lists[position].title = ""
                allLists[allLists.indexOf(lists[position])].title = ""

                if (title.isNotEmpty()) {

                    while (lists.any { lists -> lists.title == title }) {
                        title += "#"
                    }

                    lists[position].title = title
                    allLists[allLists.indexOf(lists[position])].title = title
                    lists[position].items.clear()
                    allLists[allLists.indexOf(lists[position])].items.clear()
                    lists[position].items.addAll(items)
                    allLists[allLists.indexOf(lists[position])].items.addAll(items)
                    lists[position].selected.clear()
                    allLists[allLists.indexOf(lists[position])].selected.clear()
                    lists[position].selected.addAll(selected)
                    allLists[allLists.indexOf(lists[position])].selected.addAll(selected)

                } else if (items.isNotEmpty()) {
                    lists[position].title = "-sin titulo-"
                    allLists[allLists.indexOf(lists[position])].title = "-sin titulo-"
                    lists[position].items.clear()
                    allLists[allLists.indexOf(lists[position])].items.clear()
                    lists[position].items.addAll(items)
                    allLists[allLists.indexOf(lists[position])].items.addAll(items)
                    lists[position].selected.clear()
                    allLists[allLists.indexOf(lists[position])].selected.clear()
                    lists[position].selected.addAll(selected)
                    allLists[allLists.indexOf(lists[position])].selected.addAll(selected)
                }

            }

            listsAdapter.lists = lists
            listsAdapter.notifyDataSetChanged()

            tvListTag.isVisible = lists.isNotEmpty()
            prefs.saveLists(allLists)
        }

    private fun onNoteSelected(position: Int) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("title", notes[position].title)
        intent.putExtra("text", notes[position].text)
        intent.putExtra("position", position)
        editNoteLauncher.launch(intent)
    }

    private fun onListSelected(position: Int) {
        val intent = Intent(this, EditListActivity::class.java)
        intent.putExtra("title", lists[position].title)
        intent.putStringArrayListExtra("items", ArrayList<String>(lists[position].items))
        intent.putIntegerArrayListExtra("selected", ArrayList<Int>(lists[position].selected))
        intent.putExtra("position", position)
        editListLauncher.launch(intent)
    }
}

