package com.example.learnk.Project

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface NotesApi {

//add notes
    @POST("notes")
    fun addNote(@Body note: Note): Single<Unit>

    // Get all notes
    @GET("notes")
    fun getAllNotes(): Single<List<Note>>

    @PUT("notes/{id}")
    fun updateNote(
        @Path("id") id: Int,
        @Body note: Note
    ): Single<Unit>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id: Int): Single<Response<Void>>



}