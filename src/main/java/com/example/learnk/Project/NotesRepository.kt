package com.example.learnk.Project

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class NotesRepository(private val api: NotesApi) {

    // Get all notes
    fun getAllNotes(): Single<List<Note>> {
        return api.getAllNotes()
    }

    fun addNote(note: Note): Single<Unit> {
        return api.addNote(note)
    }
//
//
    // Update a note
fun updateNote(id: Int, title: String, content: String): Single<Unit> {
    val note = Note(
        id = id,
        title = title,
        content = content
    )
    return api.updateNote(id, note)
}
//
// Function to delete a note
fun deleteNote(noteId: Int): Single<Response<Void>> {
    return api.deleteNote(noteId)
}
}
