package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {


    override val viewBinding: FragmentNoteBinding by viewBinding()

    private val args: NoteFragmentArgs by navArgs()

    private val viewModel: NoteViewModel by viewModel()

    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnConfirm.setOnClickListener {
            if (viewBinding.etInfo.text.isNotBlank()) {

                args.noteToEdit?.let {
                    viewModel.updateNote(
                        Note(
                            id = it.id,
                            title = viewBinding.etInfo.text.toString(),
                            date = dateFormatter.format(viewBinding.dataPicker.getSelectedDate()),
                            userId = it.userId
                        )
                    )
                } ?: kotlin.run {
                    viewModel.addNewNote(
                        Note(
                            title = viewBinding.etInfo.text.toString(),
                            date = dateFormatter.format(viewBinding.dataPicker.getSelectedDate())
                        )
                    )
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please, type the note", Toast.LENGTH_LONG).show()
            }
        }

        args.noteToEdit?.let { noteToEdit ->
            viewBinding.etInfo.setText(noteToEdit.title)
            viewBinding.dataPicker.setSelectedDate(noteToEdit.date)
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
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
}
