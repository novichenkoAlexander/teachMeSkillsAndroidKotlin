package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentNoteBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment

class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {


    override val viewBinding: FragmentNoteBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnConfirm.setOnClickListener {
            setFragmentResult(NEW_RESULT, Bundle().apply {
                putString(INFO, viewBinding.etInfo.text.toString())
                putString(DATE, viewBinding.etDate.text.toString())
                //TODO: Add dataPiker to field Date
                //TODO: Input data make with button and on click choose date
            })
            findNavController().popBackStack()
            viewBinding.etInfo.setText("")
            viewBinding.etDate.setText("")
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