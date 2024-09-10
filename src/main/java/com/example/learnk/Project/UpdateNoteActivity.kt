package com.example.learnk.Project
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.learnk.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        // Initialize the ViewModel
        val repository = NotesRepository(RetrofitInstance.api)
        viewModel = NotesViewModel(repository)

        val noteId = intent.getIntExtra("noteId", -1)
        val noteTitle = intent.getStringExtra("noteTitle") ?: ""
        val noteDescription = intent.getStringExtra("noteDescription") ?: ""

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val descriptionEditText = findViewById<EditText>(R.id.editTextDescription)
        val updateButton = findViewById<Button>(R.id.buttonUpdate)

        titleEditText.setText(noteTitle)
        descriptionEditText.setText(noteDescription)

        updateButton.setOnClickListener {
            val newTitle = titleEditText.text.toString()
            val newDescription = descriptionEditText.text.toString()

            viewModel.updateNote(noteId, newTitle, newDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK) // Indicate success
                    finish() // Close the activity and return to the previous screen
                }, { error ->
                    Toast.makeText(this, "Error updating note: ${error.message}", Toast.LENGTH_SHORT).show()
                })
        }
    }
}

