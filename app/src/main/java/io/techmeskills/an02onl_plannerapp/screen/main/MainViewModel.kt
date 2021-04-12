package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class MainViewModel : CoroutineViewModel() {

    val listLiveData = MutableLiveData<List<Note>>()
    private val listOfNotes = ArrayList<Note>()

    fun addNote(note: Note) {
        launch {
            val maxIdNote: Long =
                if (listOfNotes.isEmpty()) -1 else listOfNotes.reduce(Note.Compare::max).id
            listOfNotes.add(Note(maxIdNote + 1, note.title, note.date))
            listLiveData.postValue(listOfNotes)
        }
    }

    fun editNote(note: Note) {
        launch {
            val pos = listOfNotes.indexOfFirst { it.id == note.id }
            listOfNotes.removeAt(pos)
            listOfNotes.add(pos, note)
            listLiveData.postValue(listOfNotes)
        }
    }

    fun deleteNote(pos: Int) {
        launch {
            listOfNotes.removeAt(pos)
            listLiveData.postValue(listOfNotes)
        }
    }

}

class Note(
    val id: Long,
    val title: String,
    val date: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()
    )

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    internal object Compare {
        fun max(o1: Note, o2: Note): Note {
            return if (o1.id > o2.id) o1 else o2
        }
    }
}