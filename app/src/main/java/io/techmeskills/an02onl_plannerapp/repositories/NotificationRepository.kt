package io.techmeskills.an02onl_plannerapp.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.notification.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationRepository(private val context: Context, private val alarmManager: AlarmManager) {

    private val dateAndTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun setNotification(note: Note) {
        val calendar = Calendar.getInstance()
        if (note.date.contains("[\\s+]")) {
            calendar.time = dateAndTimeFormatter.parse(note.date)
        } else {
            calendar.time = dateFormatter.parse(note.date)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            makeIntent(note)
        )
    }

    fun unsetNotification(note: Note) {
        alarmManager.cancel(makeIntent(note))
    }

    private fun makeIntent(note: Note): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = INTENT_ACTION
        intent.putExtra(NOTE_ID_KEY, note.id)
        intent.putExtra(NOTE_TEXT_KEY, note.title)
        intent.putExtra(USER_NAME_KEY, note.userName)
        return PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
    }

    companion object {
        private const val INTENT_ACTION = "PLANNER_APP_NOTIFICATION"
        const val NOTE_TEXT_KEY = "PLANNER_APP_NOTIFICATION_TEXT"
        const val USER_NAME_KEY = "PLANNER_APP_NOTIFICATION_USER"
        const val NOTE_ID_KEY = "PLANNER_APP_NOTE_ID"
    }

}