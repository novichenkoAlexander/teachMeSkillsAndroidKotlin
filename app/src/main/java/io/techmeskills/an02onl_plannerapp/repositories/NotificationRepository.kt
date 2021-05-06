package io.techmeskills.an02onl_plannerapp.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import io.techmeskills.an02onl_plannerapp.models.Note
import io.techmeskills.an02onl_plannerapp.notification.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationRepository(private val context: Context, private val alarmManager: AlarmManager) {

    private val dataAndTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun setNotification(note: Note) {

        val calendar = Calendar.getInstance()
        calendar.time = dataAndTimeFormatter.parse("${note.date} ${note.time}")
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
        intent.putExtra(INTENT_TEXT, note.title)
        intent.putExtra(INTENT_USER, note.userName)
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    companion object {
        private const val INTENT_ACTION = "PLANNER_APP_NOTIFICATION"
        const val INTENT_TEXT = "PLANNER_APP_NOTIFICATION_TEXT"
        const val INTENT_USER = "PLANNER_APP_NOTIFICATION_USER"
    }

}