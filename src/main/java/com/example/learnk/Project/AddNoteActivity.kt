package com.example.learnk.Project

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learnk.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import android.app.Activity

class AddNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Initialize the ViewModel
        val repository = NotesRepository(RetrofitInstance.api)
        viewModel = NotesViewModel(repository)

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val descriptionEditText = findViewById<EditText>(R.id.editTextDescription)
        val addButton = findViewById<Button>(R.id.buttonAddNote)

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            viewModel.addNote(title, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK) // Indicate success
                    finish() // Close the activity and return to the previous screen
                }, { error ->
                    Toast.makeText(this, "Error adding note: ${error.message}", Toast.LENGTH_SHORT).show()
                })
        }
    }
}
