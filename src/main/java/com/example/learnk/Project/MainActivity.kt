package com.example.learnk.Project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnk.R
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel
        val repository = NotesRepository(RetrofitInstance.api)
        viewModel = NotesViewModel(repository)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        notesAdapter = NotesAdapter(listOf(), onUpdateClick = { note ->
            // Open UpdateNoteActivity and pass the note details
            val intent = Intent(this, UpdateNoteActivity::class.java).apply {
                putExtra("noteId", note.id)
                putExtra("noteTitle", note.title)
                putExtra("noteDescription", note.content)
            }
            startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE)
        }, onDeleteClick = { note ->
            viewModel.deleteNote(note.id)
        })

        recyclerView.adapter = notesAdapter

        // Observe notesLiveData to update the RecyclerView whenever notes data changes
        viewModel.notesLiveData.observe(this) { notes ->
            notesAdapter.updateNotes(notes)
        }

        // Fetch all notes
        viewModel.getAllNotes()

        // Handle the "Add Note" button click
        val addNoteButton = findViewById<Button>(R.id.addNotes)
        addNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Refresh notes after adding or updating a note
        if ((requestCode == REQUEST_CODE_UPDATE_NOTE || requestCode == ADD_NOTE_REQUEST_CODE)
            && resultCode == Activity.RESULT_OK) {
            viewModel.getAllNotes() // Refresh the list after update or add
        }
    }

    companion object {
        private const val REQUEST_CODE_UPDATE_NOTE = 2
        private const val ADD_NOTE_REQUEST_CODE = 1
    }
}


