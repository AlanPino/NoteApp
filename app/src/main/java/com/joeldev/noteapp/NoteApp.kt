package com.joeldev.noteapp

import android.app.Application

class NoteApp: Application() {

    companion object{
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}