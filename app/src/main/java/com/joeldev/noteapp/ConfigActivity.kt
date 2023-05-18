package com.joeldev.noteapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.joeldev.noteapp.NoteApp.Companion.prefs


class ConfigActivity : AppCompatActivity() {
    private lateinit var cTheme: LinearLayout
    private lateinit var spThemes: Spinner
    private var update = false

    private val themes = arrayOf("tema 1", "tema 2", "tema 3", "tema 4", "tema 5")

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(prefs.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        initComp()
        initUI()
        initListeners()
    }

    private fun initUI() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spThemes.adapter = adapter

        spThemes.setSelection(prefs.getThemePosition())
    }

    private fun initListeners() {
        spThemes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            var theme: Int = prefs.getTheme()

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val themeSelected = when(position){
                    0 -> R.style.Theme_NoteApp_Theme1
                    1 -> R.style.Theme_NoteApp_Theme2
                    2 -> R.style.Theme_NoteApp_Theme3
                    3 -> R.style.Theme_NoteApp_Theme4
                    4 -> R.style.Theme_NoteApp_Theme5

                    else -> theme
                }


                if(themeSelected != theme){
                    prefs.saveTheme(themeSelected)
                    prefs.saveThemePosition(position)
                    update = true
                    recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }

    private fun initComp() {
        cTheme = findViewById(R.id.cThemes)
        spThemes = findViewById(R.id.spThemes)
    }
}