package io.techmeskills.an02onl_plannerapp.screen.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import java.text.SimpleDateFormat
import java.util.*


class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {


    override val viewBinding: FragmentNoteBinding by viewBinding()
    private val args: NoteFragmentArgs by navArgs()
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.noteToEdit?.let { noteToEdit ->
            viewBinding.etInfo.setText(noteToEdit.title)
            viewBinding.dataPicker.setSelectedDate(noteToEdit.date)
        }

        viewBinding.btnConfirm.setOnClickListener {
            if (viewBinding.etInfo.text.isNotBlank()) {
                setFragmentResult(NEW_RESULT, Bundle().apply {
                    putParcelable(
                        NOTE,
                        Note(
                            if (args.noteToEdit == null) -1 else args.noteToEdit!!.id,
                            viewBinding.etInfo.text.toString(),
                            dateFormatter.format(viewBinding.dataPicker.getSelectedDate())
                        )
                    )
                })
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please, type the note", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

    private fun DatePicker.getSelectedDate(): Date {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(Calendar.YEAR, this.year)
        calendar.set(Calendar.MONTH, this.month)
        calendar.set(Calendar.DAY_OF_MONTH, this.dayOfMonth)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    private fun DatePicker.setSelectedDate(date: String?) {
        date?.let {
            dateFormatter.parse(it)?.let { date ->
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.time = date
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                this.updateDate(year, month, day)
            }
        }
    }

    companion object {
        const val NEW_RESULT = "New Result"
        const val NOTE = "NOTE"
    }
}