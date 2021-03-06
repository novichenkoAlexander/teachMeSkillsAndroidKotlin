package io.techmeskills.an02onl_plannerapp.screen.noteDetails

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeValue
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.CalendarView
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

    private val dayFormatter = SimpleDateFormat("dd.EEE.2021", Locale.getDefault())

    private val timeFormatter: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val calendar = Calendar.getInstance(Locale.getDefault())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isNotified = false

        viewBinding.swNotify.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isNotified = true
                buttonView.text = resources.getString(R.string.notify)
            } else {
                isNotified = false
                buttonView.text = resources.getString(R.string.not_notify)
            }
        }

        viewBinding.btnConfirm.setOnClickListener {
            if (viewBinding.etInfo.text.isNotBlank()) {

                args.noteToEdit?.let {
                    viewModel.updateNote(
                        Note(
                            id = it.id,
                            title = viewBinding.etInfo.text.toString(),
                            date = "${viewBinding.btnDate.text} ${viewBinding.btnTime.text}",
                            userName = it.userName,
                            isNotified = isNotified
                        )
                    )
                } ?: kotlin.run {
                    viewModel.addNewNote(
                        Note(
                            title = viewBinding.etInfo.text.toString(),
                            date = "${viewBinding.btnDate.text} ${viewBinding.btnTime.text}",
                            userName = "",
                            isNotified = isNotified
                        )
                    )
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please, type the note", Toast.LENGTH_LONG).show()
            }
        }

        viewBinding.btnDate.setOnClickListener {
            showDatePickerDialog()
        }
        viewBinding.btnTime.setOnClickListener {
            showTimePickerDialog()
        }

        args.noteToEdit?.let { noteToEdit ->
            viewBinding.etInfo.setText(noteToEdit.title)
            if (noteToEdit.date.isNotBlank()) {
                viewBinding.btnDate.text = noteToEdit.date.substring(0, 10)
                viewBinding.btnTime.text = noteToEdit.date.substring(10)
            }
        }

        viewBinding.calendarView.onDateChangeCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                viewBinding.btnDate.text = dayFormatter.format(date)
            }
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

    @Suppress("DEPRECATION")
    @SuppressLint("ResourceType")
    private fun showTimePickerDialog() {
        val dialog = SnapTimePickerDialog.Builder().apply {
            setTitle(R.string.time_picker_title)
            setPrefix(R.string.time_picker_prefix)
            setSuffix(R.string.time_picker_suffix)
            setThemeColor(R.color.purple_500)
            setTitleColor(R.color.white)
            setPreselectedTime(
                TimeValue(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)
                )
            )
        }.build().apply {
            setListener { hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val time = calendar.time
                viewBinding.btnTime.text = timeFormatter.format(time)
            }
        }
        this.fragmentManager?.let { dialog.show(it, SnapTimePickerDialog.TAG) }
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            requireContext(), dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        setInitDate()
    }

    private fun setInitDate() {
        viewBinding.btnDate.text = dateFormatter.format(calendar.time)
    }

}
