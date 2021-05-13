package io.techmeskills.an02onl_plannerapp.screen.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.models.Note


class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit
) :
    ListAdapter<Note, NotesRecyclerViewAdapter.NoteViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
        ::onItemClick
    )

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    override fun onBindViewHolder(holder: NotesRecyclerViewAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        private val ivCloud = itemView.findViewById<ImageView>(R.id.ivCloud)
        private val ivIsNotified = itemView.findViewById<ImageView>(R.id.ivNotify)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = item.date
//            tvTime.text = item.time
            ivCloud.isVisible = item.fromCloud
            ivIsNotified.isVisible = item.isNotified
        }
    }
}

class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.date == newItem.date && oldItem.title == newItem.title && oldItem.fromCloud == newItem.fromCloud
    }
}

