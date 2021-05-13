package io.techmeskills.an02onl_plannerapp.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class AlarmService : Service(), KoinComponent {

    private val notesRepository: NotesRepository by inject()

    private var noteId: Long = -1


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            noteId = it.getLongExtra(AlarmReceiver.NOTIFICATION_KEY_NOTE_ID, -1)
            when (it.action) {
                AlarmReceiver.ACTION_DELETE -> {
                    GlobalScope.launch { notesRepository.deleteNoteById(noteId) }
                }
                else -> Unit
            }
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}