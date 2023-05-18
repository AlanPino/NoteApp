package com.joeldev.noteapp

data class List(
    var title: String,
    var items: MutableList<String>,
    var selected: MutableList<Int>,
    var folder: String
)
