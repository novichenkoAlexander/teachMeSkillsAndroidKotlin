package io.techmeskills.an02onl_plannerapp.repositories

import io.techmeskills.an02onl_plannerapp.cloud.ApiInterface
import io.techmeskills.an02onl_plannerapp.cloud.CloudNote
import io.techmeskills.an02onl_plannerapp.cloud.CloudUser
import io.techmeskills.an02onl_plannerapp.cloud.UploadNotesRequestBody
import io.techmeskills.an02onl_plannerapp.models.Note
import kotlinx.coroutines.flow.first

class CloudRepository(
    private val apiInterface: ApiInterface,
    private val usersRepository: UsersRepository,
    private val notesRepository: NotesRepository
) {

    suspend fun exportNotes(): Boolean {
        val user = usersRepository.getCurrentUserById().first()
        val notes = notesRepository.getCurrentUsersNotes()
        val cloudUser = CloudUser(userName = user.name)
        val cloudNotes =
            notes.map { cloudNote -> CloudNote(noteTitle = cloudNote.title, date = cloudNote.date) }
        val uploadNotesRequestBody = UploadNotesRequestBody(cloudUser, usersRepository.phoneId, cloudNotes)
        val uploadResult = apiInterface.exportNotes(uploadNotesRequestBody).isSuccessful
        if (uploadResult) {
            notesRepository.setAllNotesSyncWithCloud()
        }
        return uploadResult
    }

    suspend fun importNotes(): Boolean {
        val user = usersRepository.getCurrentUserById().first()
        val response = apiInterface.importNotes(user.name, usersRepository.phoneId)
        val cloudNotes = (response.body() ?: emptyList()).map { cloudNote ->
            Note(
                title = cloudNote.noteTitle,
                date = cloudNote.date,
                userName = user.name,
                fromCloud = true
            )
        }
        val currentUserNotes = notesRepository.getCurrentUsersNotes()
        val resultNotesList = (cloudNotes + currentUserNotes).distinctBy { it.title + it.date }

        notesRepository.addNotes(resultNotesList)
        return response.isSuccessful
    }
}
