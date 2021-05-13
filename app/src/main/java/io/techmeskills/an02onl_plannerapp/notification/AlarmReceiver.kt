package io.techmeskills.an02onl_plannerapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.repositories.NotificationRepository
import org.koin.core.component.KoinApiExtension

class AlarmReceiver : BroadcastReceiver() {

    @KoinApiExtension
    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context, intent)
    }

    @KoinApiExtension
    private fun showNotification(context: Context, intent: Intent) {
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, AlarmReceiver::class.java), 0
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager)
        }

        val noteOwner = intent.getStringExtra(NotificationRepository.USER_NAME_KEY)
        val noteTitle = intent.getStringExtra(NotificationRepository.NOTE_TEXT_KEY)
        val noteId = intent.getLongExtra(NotificationRepository.NOTE_ID_KEY, -1)

        val alarmBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(noteOwner)
            .setContentText(noteTitle)
            .setContentIntent(contentIntent)
            .addAction(deleteAction(context, noteId))
            .setDefaults(Notification.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(0, alarmBuilder.build())
    }

    @KoinApiExtension
    private fun deleteAction(context: Context, noteId: Long): NotificationCompat.Action {
        val deleteIntent = Intent(context.applicationContext, AlarmService::class.java)
        deleteIntent.action = ACTION_DELETE
        deleteIntent.putExtra(NOTIFICATION_KEY_NOTE_ID, noteId)

        val deletePendingIntent =
            PendingIntent.getService(
                context.applicationContext,
                1111,
                deleteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        return NotificationCompat.Action.Builder(
            R.drawable.ic_trash_icon, "Delete", deletePendingIntent
        ).build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationManager: NotificationManager) {
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL) == null) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL = "PLANNER_APP_NOTIFICATION_CHANNEL"
        const val NOTIFICATION_KEY_NOTE_ID = "PLANNER_APP_NOTIFICATION_KEY_NOTE_ID"
        const val ACTION_DELETE = "PLANNER_APP_ACTION_DELETE"

    }
}