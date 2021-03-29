package io.techmeskills.an02onl_plannerapp.screen.main

import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel

class MainViewModel : CoroutineViewModel() {

    var notes: ArrayList<Note> = ArrayList()
}

class Note(
    val title: String,
    //val date: String? = null
)