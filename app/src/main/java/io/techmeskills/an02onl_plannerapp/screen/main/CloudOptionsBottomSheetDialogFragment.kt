package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.techmeskills.an02onl_plannerapp.R


class CloudOptionsBottomSheetDialogFragment(
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
        val exportNotes = view.findViewById<TextView>(R.id.tvExportNotes)
        val importNotes = view.findViewById<TextView>(R.id.tvImportNotes)

        exportNotes.setOnClickListener {

        }

        importNotes.setOnClickListener {

        }
        return view
    }

}