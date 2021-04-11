package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment


class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {


    override val viewBinding: FragmentNoteBinding by viewBinding()
    private val args: NoteFragmentArgs by navArgs()
    private var date = "2021:01:01"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.noteToEdit?.let { noteToEdit ->
            viewBinding.etInfo.setText(noteToEdit.title)
            viewBinding.btnSetDate.text = noteToEdit.date
        }

        viewBinding.dataPicker.init(2021, 1, 1) { _, year, monthOfYear, dayOfMonth ->
            date = "$year : $monthOfYear : $dayOfMonth"
        }

        viewBinding.btnSetDate.setOnClickListener {
            viewBinding.btnSetDate.text = date
        }

        viewBinding.btnConfirm.setOnClickListener {
            if (viewBinding.etInfo.text.isNotBlank()) {
                setFragmentResult(NEW_RESULT, Bundle().apply {
                    putParcelable(
                        NOTE,
                        Note(
                            if (args.noteToEdit == null) -1 else args.noteToEdit!!.id,
                            viewBinding.etInfo.text.toString(),
                            viewBinding.btnSetDate.text.toString()
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

    companion object {
        const val NEW_RESULT = "New Result"
        const val NOTE = "NOTE"
    }
}