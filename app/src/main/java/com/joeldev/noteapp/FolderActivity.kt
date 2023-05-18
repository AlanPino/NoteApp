package com.joeldev.noteapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joeldev.noteapp.NoteApp.Companion.prefs

class FolderActivity : AppCompatActivity() {

    private lateinit var tvBanner: TextView
    private lateinit var rvFolders: RecyclerView
    private lateinit var foldersAdapter: FoldersAdapter
    private lateinit var fabAddFolder: FloatingActionButton
    private lateinit var btnOptions: ImageButton
    private lateinit var options: PopupMenu

    private val folders = prefs.getFolders()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(prefs.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        initComp()
        initUI()
        initListeners()
        initPopupMenu()
    }


    private fun initComp() {
        rvFolders = findViewById(R.id.rvFolders)
        fabAddFolder = findViewById(R.id.fabAddFolder)
        tvBanner = findViewById(R.id.tvBanner)
        btnOptions = findViewById(R.id.btnOptions)
    }

    private fun initUI() {
        foldersAdapter = FoldersAdapter(folders) { onFolderSelected(it) }
        rvFolders.layoutManager = LinearLayoutManager(this)
        rvFolders.adapter = foldersAdapter
    }

    private fun initListeners() {
        fabAddFolder.setOnClickListener {
            showDialog()
        }

        btnOptions.setOnClickListener {
            options.show()
        }
    }

    private fun onFolderSelected(position: Int) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("folderName", folders[position])
        startActivity(intent)
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_folder)

        val btnCreateFolder: Button = dialog.findViewById(R.id.btnCreateFolder)
        val etFolder: EditText = dialog.findViewById(R.id.etFolder)

        dialog.show()

        btnCreateFolder.setOnClickListener {
            val text: String = etFolder.text.toString()

            if (text.isNotEmpty()) {
                if (folders.contains(text)) {
                    Toast.makeText(this, "este nombre ya existe", Toast.LENGTH_SHORT).show()
                } else {
                    folders.add(etFolder.text.toString())
                    dialog.hide()
                }
            }

            prefs.saveFolders(folders)

        }
    }

    private fun initPopupMenu() {
        options = PopupMenu(this, btnOptions)
        options.menu.add(Menu.NONE, 1, Menu.NONE, "configuraciones")

        options.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                1 -> {
                    val intent = Intent(this, ConfigActivity::class.java)
                    configLauncher.launch(intent)
                    true
                }
                else -> false
            }
        }
    }

    private var configLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            recreate()
        }
}