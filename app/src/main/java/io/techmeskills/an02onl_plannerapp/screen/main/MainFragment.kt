package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import androidx.core.view.size
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toNoteFragment())
        }

        setFragmentResultListener(NoteFragment.NEW_RESULT) { _, bundle ->
            val info = bundle.getString(NoteFragment.INFO)
            val date = bundle.getString(NoteFragment.DATE)
            info?.let {
                viewModel.addNoteToList(it, date!!)
            }
        }
        viewBinding.recycleView.scrollToPosition(viewBinding.recycleView.size - 1)
        viewModel.listLiveData.observe(this.viewLifecycleOwner, {
            viewBinding.recycleView.adapter = NotesRecyclerViewAdapter(it)
        })
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.recycleView.setPadding(0, 0, 0, bottom)
        viewBinding.btnAdd.setVerticalMargin(marginBottom = bottom)
    }

}