 package io.techmeskills.an02onl_plannerapp.screen.main.repositories

import io.techmeskills.an02onl_plannerapp.screen.main.cloud.ApiInterface
import io.techmeskills.an02onl_plannerapp.screen.main.cloud.CloudNote
import io.techmeskills.an02onl_plannerapp.screen.main.cloud.CloudUser
import io.techmeskills.an02onl_plannerapp.screen.main.cloud.UploadNotesRequestBody
import io.techmeskills.an02onl_plannerapp.screen.main.models.Note
import kotlinx.coroutines.flow.first

class CloudRepository(
    private val apiInterface: ApiInterface,
    private val usersRepository: UsersRepository,
    private val notesRepository: NotesRepository
) {

    suspend fun uploadNotes(): Boolean {
        val user = usersRepository.getCurrentUserById().first()
        val notes = notesRepository.getCurrentUsersNotes()
        val cloudUser = CloudUser(userId = user.id, userName = user.name)
        val cloudNotes =
            notes.map { cloudNote -> CloudNote(noteId = cloudNote.id, noteTitle = cloudNote.title, date = cloudNote.date) }
        val uploadNotesRequestBody = UploadNotesRequestBody(cloudUser, usersRepository.phoneId, cloudNotes)
        val uploadResult = apiInterface.uploadNotes(uploadNotesRequestBody).isSuccessful
        print("UPLOAD: $uploadResult")
        if (uploadResult) {
            notesRepository.setAllNotesSyncWithCloud()
        }
        return uploadResult
    }

    suspend fun downloadNotes(): Boolean {
        val user = usersRepository.getCurrentUserById().first()
        val response = apiInterface.downloadNotes(user.name, usersRepository.phoneId)
        val cloudNotes = response.body() ?: emptyList()
        val notes = cloudNotes.map { cloudNote ->
            Note(
                title = cloudNote.noteTitle,
                date = cloudNote.date,
                userId = user.id,
                fromCloud = true
            )
        }
        notesRepository.addNotes(notes)
        return response.isSuccessful
    }
}
