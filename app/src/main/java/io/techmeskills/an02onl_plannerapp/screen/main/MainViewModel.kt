package io.techmeskills.an02onl_plannerapp.screen.main

import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel

class MainViewModel : CoroutineViewModel() {

    val notes = listOf(
        Note("Wake up!"),
        Note("Stand up"),
        Note("Brush your teeth"),
        Note("Make breakfast"),
        Note("Make tea"),
        Note("Clean your head"),
        Note("Make some exercise"),
        Note("Read some pages of book"),
        Note("Dress up"),
        Note("And go to work")
    )

}

class Note(
    val title: String,
    val date: String? = null
)