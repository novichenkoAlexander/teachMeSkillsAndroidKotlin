package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class MainViewModel : CoroutineViewModel() {

    val listLiveData = MutableLiveData<List<Note>>()
    private val listOfNotes = ArrayList<Note>()

    fun addNoteToList(text: String, date: String?) {
        launch {
            val note = Note(text, date)
            if (note.title.isNotBlank()) {
                listOfNotes.add(note)
            }
            listLiveData.postValue(listOfNotes)
        }
    }

}

class Note(
    val title: String,
    val date: String? = null
)