package io.techmeskills.an02onl_plannerapp.screen.main

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.view.size
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.ContentHandler

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private val adapter = NotesRecyclerViewAdapter(
        onClick = { note ->
            findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(note))
        },
        onDelete = {
            viewModel.deleteNote(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter

        viewModel.listLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.btnAddNote.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(null))
        }

        setFragmentResultListener(NoteFragment.NEW_RESULT) { _, bundle ->
            bundle.getParcelable<Note>(NoteFragment.NOTE)?.let {
                if (it.id < 0) {
                    viewModel.addNote(it)
                } else {
                    viewModel.editNote(it)
                }
            }
        }
        viewBinding.recyclerView.scrollToPosition(viewBinding.recyclerView.size - 1)

        val simpleSwipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                c.clipRect(0f, viewHolder.itemView.top.toFloat(), dX, viewHolder.itemView.bottom.toFloat())
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            }
        }
        val noteHelper = ItemTouchHelper(simpleSwipeCallBack)
        noteHelper.attachToRecyclerView(viewBinding.recyclerView)
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
        viewBinding.btnAddNote.setVerticalMargin(marginBottom = bottom)
    }

}
