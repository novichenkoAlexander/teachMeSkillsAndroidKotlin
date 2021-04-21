package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private val args: MainFragmentArgs by navArgs()

    private val adapter = NotesRecyclerViewAdapter(
        onClick = ::onItemClick
    )

    private fun onItemClick(note: Note) {
        findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(note))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter
        viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.btnAddNote.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(null))
        }

        viewBinding.ivLogOut.setOnClickListener {
            viewModel.logOut()
            findNavController().navigateSafe(MainFragmentDirections.toLoginScreenFragment())
        }

        viewBinding.recyclerView.smoothScrollToPosition(adapter.itemCount)

        val simpleSwipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(viewHolder.adapterPosition)
            }
        }
        val noteHelper = ItemTouchHelper(simpleSwipeCallBack)
        noteHelper.attachToRecyclerView(viewBinding.recyclerView)

        args.userName.let { userName ->
            viewBinding.toolbar.title = userName
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
        viewBinding.btnAddNote.setVerticalMargin(marginBottom = bottom)
    }

}
