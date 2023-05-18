package com.joeldev.noteapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(val context: Context) {

    val SHARED_NAME = "database"
    val SHARED_THEME = "theme"
    val SHARED_THEME_POSITION = "theme_position"
    val SHARED_LISTS = "lists"
    val SHARED_NOTES = "notes"
    val SHARED_FOLDERS = "folders"

    var storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveTheme(theme: Int){
        storage.edit().putInt(SHARED_THEME, theme).apply()
    }

    fun saveThemePosition(position: Int){
        storage.edit().putInt(SHARED_THEME_POSITION, position).apply()
    }

    fun saveNotes(notes: MutableList<Note>){
        val jsonNotes = Gson().toJson(notes)
        storage.edit().putString(SHARED_NOTES, jsonNotes).apply()
    }

    fun saveLists(lists: MutableList<List>){
        val jsonLists = Gson().toJson(lists)
        storage.edit().putString(SHARED_LISTS, jsonLists).apply()
    }

    fun saveFolders(folders: MutableList<String>){
        storage.edit().putString(SHARED_FOLDERS, folders.joinToString(",")).apply()
    }

    fun getTheme(): Int{
        return storage.getInt(SHARED_THEME, R.style.Theme_NoteApp_Theme4)
    }

    fun getThemePosition(): Int{
        return storage.getInt(SHARED_THEME_POSITION, 3)
    }

    fun getNotes():MutableList<Note>{
        val jsonNotes = storage.getString(SHARED_NOTES, "")
        val type = object : TypeToken<MutableList<Note>>() {}.type
        return Gson().fromJson(jsonNotes, type) ?: mutableListOf()
    }

    fun getLists():MutableList<List>{
        val jsonList = storage.getString(SHARED_LISTS, "")
        val type = object : TypeToken<MutableList<List>>() {}.type
        return Gson().fromJson(jsonList, type) ?: mutableListOf()
    }

    fun getFolders():MutableList<String>{
        return storage.getString(SHARED_FOLDERS, "mi carpeta")?.split(",")?.toMutableList() ?: mutableListOf()
    }
}