package io.techmeskills.an02onl_plannerapp.screen.main

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : CoroutineViewModel() {

    val listLiveData = MutableLiveData<List<Note>>()
    private val listOfNotes = ArrayList<Note>()

    fun addNoteToList(view: EditText) {
        val note = Note(view.text.toString(), Date().toString())
        launch {
            if (note.title.isNotBlank() && note.title.isNotEmpty()) {
                listOfNotes.add(note)
            }
            listLiveData.postValue(listOfNotes)
        }
    }

}

class Note(
    val title: String,
    val date: String
)