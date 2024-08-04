package com.hadesmori.notes.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.hadesmori.notes.R
import com.hadesmori.notes.databinding.ActivityNoteDetailBinding
import com.hadesmori.notes.domain.model.Note

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.ivBack.setOnClickListener{ saveNote() }
    }

    private fun saveNote() {
        val newTitle = binding.etTitle.text.toString()
        val newBody = binding.etBody.text.toString()
        val newNote = Note(newTitle, newBody)

        //val navController = findNavController(R.id.fragmentContainer)
       // navController.previousBackStackEntry?.savedStateHandle?.set("newNote", newNote)
        //navController.popBackStack()
        //finish()

        val resultIntent = Intent().apply {
            putExtra("newNote", newNote)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}