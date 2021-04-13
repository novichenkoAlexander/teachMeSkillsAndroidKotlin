package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel : CoroutineViewModel() {

    val listLiveData = MutableLiveData(
        listOf(
            Note(0, "Wake up"),
            Note(1, "GO for a walk"),
            Note(2, "DO something"),
            Note(3, "Hello"),
            Note(4, "Hello world"),
            Note(5, "Back home"),
            Note(6, "Make breakfast"),
            Note(7, "Clean teeth"),
            Note(8, "Make coffee"),
            Note(9, "Take sandwich"),
            Note(10, "Be late to work"),
            )
    )

    fun addNote(note: Note) {
        launch {
            val list = listLiveData.value!!.toMutableList()
            val maxIdNote: Long =
                if (list.isEmpty()) -1 else list.reduce(Note.Compare::max).id
            list.add(Note(maxIdNote + 1, note.title, note.date))
            listLiveData.postValue(list)
        }
    }

    fun editNote(note: Note) {
        launch {
            val list = listLiveData.value!!.toMutableList()
            val pos = list.indexOfFirst { it.id == note.id }
            list.removeAt(pos)
            list.add(pos, note)
            listLiveData.postValue(list)
        }
    }

    fun deleteNote(pos: Int) {
        launch {
            val list = listLiveData.value!!.toMutableList()
            list.removeAt(pos)
            listLiveData.postValue(list)
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