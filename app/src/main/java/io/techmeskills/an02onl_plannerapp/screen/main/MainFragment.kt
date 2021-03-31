package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnClicker.setOnClickListener {
            viewModel.addNoteToList(viewBinding.editText)
            viewModel.listLiveData.observe(this.viewLifecycleOwner, {
                viewBinding.recycleView.adapter = NotesRecyclerViewAdapter(it)
            })
        }

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.mainLayout.setPadding(0, 0, 0, bottom)
    }

}