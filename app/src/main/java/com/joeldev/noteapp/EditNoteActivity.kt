package com.joeldev.noteapp


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.cardview.widget.CardView
import com.joeldev.noteapp.NoteApp.Companion.prefs
import kotlin.properties.Delegates

class EditNoteActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etText: EditText
    private lateinit var card: CardView
    private var position by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(prefs.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val title = intent.getStringExtra("title").orEmpty()
        val text = intent.getStringExtra("text").orEmpty()
        position = intent.getIntExtra("position", -1)

        initComp()
        initListeners()

        etTitle.append(title)
        etText.append(text)
    }

    private fun initListeners() {
        card.setOnClickListener {
            etText.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onBackPressed() {
        returnIntent()
    }

    private fun returnIntent() {
        val intent = Intent()
        intent.putExtra("title", etTitle.text.toString())
        intent.putExtra("text", etText.text.toString())
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initComp() {
        etTitle = findViewById(R.id.etTitle)
        etText = findViewById(R.id.etText)
        card = findViewById(R.id.card)
    }
}