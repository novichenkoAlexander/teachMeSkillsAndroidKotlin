package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment

class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {


    override val viewBinding: FragmentNoteBinding by viewBinding()
    var date = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.dataPicker.init(
            2021,
            1,
            1
        ) { _, year, monthOfYear, dayOfMonth ->
            date = "$year : $monthOfYear : $dayOfMonth"
        }

        viewBinding.btnSetDate.setOnClickListener {
            viewBinding.btnSetDate.apply {
                text = date
            }
        }

        viewBinding.btnConfirm.setOnClickListener {
            setFragmentResult(NEW_RESULT, Bundle().apply {
                putString(INFO, viewBinding.etInfo.text.toString())
                putString(DATE, viewBinding.btnSetDate.text.toString())
            })
            findNavController().popBackStack()
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
        const val INFO = "INFO"
        const val DATE = "DATE"
    }
}