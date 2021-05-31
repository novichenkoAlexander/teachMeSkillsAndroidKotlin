package io.techmeskills.an02onl_plannerapp.support

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val rvDays: RecyclerView by lazy { findViewById(R.id.rvDays) }
    private val btnToday: AppCompatButton by lazy { findViewById(R.id.btnToday) }

    var onDateChangeCallback: DateChangeListener? = null

    init {
        View.inflate(context, R.layout.calendar_view, this)
        rvDays.adapter = DaysAdapter(generateDays()) {
            onDateChangeCallback?.onDateChanged(it)
        }

        btnToday.setOnClickListener {
            rvDays.scrollToPosition(0)
        }
    }

    private fun generateDays(): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.time = Date()

        val list = arrayListOf<Date>()

        for (i in 0..30) {
            list.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }

    class DaysAdapter(
        private val items: List<Date>,
        private val onDateChangeCallback: (Date) -> Unit
    ) :
        RecyclerView.Adapter<DaysViewHolder>() {

        private var selectedDate: Date? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
            return DaysViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_day_list_item, parent, false),
                ::onItemClick
            )
        }

        private fun onItemClick(position: Int) {
            val prevPos = items.indexOf(selectedDate)
            selectedDate = items[position]
            onDateChangeCallback(items[position])
            notifyItemChanged(prevPos)
            notifyItemChanged(position)

        }

        override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
            holder.bind(items[position], selectedDate == items[position])
        }

        override fun getItemCount() = items.size
    }


    class DaysViewHolder(itemView: View, onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val tvDay = itemView.findViewById<TextView>(R.id.tvDay)
        private val tvWeekDay = itemView.findViewById<TextView>(R.id.tvWeekDay)

        init {
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun bind(date: Date, selected: Boolean) {
            tvDay.text = monthDayFormatter.format(date)
            tvWeekDay.text = dayFormatter.format(date)

            itemView.setBackgroundResource(
                if (selected) R.drawable.selected_day_background else 0
            )

            val color = if (selected) Color.WHITE else Color.BLACK

            tvDay.setTextColor(color)
            tvWeekDay.setTextColor(color)
        }

        companion object {
            val monthDayFormatter = SimpleDateFormat("dd", Locale.getDefault())
            val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
        }
    }

    interface DateChangeListener {
        fun onDateChanged(date: Date)
    }
}