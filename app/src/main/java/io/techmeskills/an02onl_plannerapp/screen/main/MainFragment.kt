package io.techmeskills.an02onl_plannerapp.screen.main

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private val adapter = NotesRecyclerViewAdapter(
        onClick = ::onItemClick
    )

    private fun onItemClick(note: Note) {
        findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(note))
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //BottomSheet
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val import = bottomSheetView.findViewById<TextView>(IMPORT_NOTES)
        val export = bottomSheetView.findViewById<TextView>(EXPORT_NOTES)
        dialog.setContentView(bottomSheetView, null)
        viewBinding.ivCloud.setOnClickListener {
            dialog.show()
            export.setOnClickListener {
                viewBinding.progressBar.isVisible = true
                viewModel.exportNotes()
                dialog.dismiss()
            }
            import.setOnClickListener {
                viewBinding.progressBar.isVisible = true
                viewModel.importNotes()
                dialog.dismiss()
            }
        }

        viewBinding.recyclerView.adapter = adapter
        viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.btnAddNote.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toNoteFragment(null))
        }

        viewBinding.ivSettings.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toSettingsFragment())
        }

        viewBinding.recyclerView.smoothScrollToPosition(adapter.itemCount)

        //Deleting note with swipe
        val simpleSwipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                //paint red background of the note while swipe
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val paint = Paint()
                    paint.color = Color.RED
                    val backGround = RectF(
                        (viewHolder.itemView.right + dX), viewHolder.itemView.top.toFloat(),
                        viewHolder.itemView.right.toFloat(), viewHolder.itemView.bottom.toFloat()
                    )
                    c.drawRect(backGround, paint)

                    // make note transparent
                    val width: Float = viewHolder.itemView.width.toFloat()
                    val alpha = 1.0f - abs(dX) / width
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val noteHelper = ItemTouchHelper(simpleSwipeCallBack)
        noteHelper.attachToRecyclerView(viewBinding.recyclerView)

        viewModel.currentUserNameLiveData.observe(this.viewLifecycleOwner) { userName ->
            viewBinding.toolbar.title = userName
        }

        viewModel.progressLifeData.observe(this.viewLifecycleOwner) { success ->
            viewBinding.progressBar.isVisible = false
            val cloudResult = if (success) R.string.cloud_success else R.string.cloud_failed
            Toast.makeText(requireContext(), cloudResult, Toast.LENGTH_LONG).show()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
        viewBinding.btnAddNote.setVerticalMargin(marginBottom = bottom)
    }

    companion object {
        private const val EXPORT_NOTES = R.id.ivExportNotes
        private const val IMPORT_NOTES = R.id.ivImportNotes
    }

}
