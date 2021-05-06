package io.techmeskills.an02onl_plannerapp.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.repositories.NotificationRepository

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent) {
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, AlarmReceiver::class.java), 0
        )

        val alarmBuilder = NotificationCompat.Builder(context, context.packageName)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(intent.getStringExtra(NotificationRepository.INTENT_USER))
            .setContentText(intent.getStringExtra(NotificationRepository.INTENT_TEXT))

        alarmBuilder.apply {
            setContentIntent(contentIntent)
            setDefaults(Notification.DEFAULT_SOUND)
            setAutoCancel(true)
        }

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, alarmBuilder.build())

    }
}