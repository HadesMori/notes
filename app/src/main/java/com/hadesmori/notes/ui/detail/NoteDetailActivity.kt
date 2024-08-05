package com.hadesmori.notes.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hadesmori.notes.databinding.ActivityNoteDetailBinding
import com.hadesmori.notes.domain.model.Note
import java.io.Serializable

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    private fun initUI() {
        note = intent.serializable<Note>("note")

        if(note != null){
            binding.etTitle.setText(note!!.title)
            binding.etBody.setText(note!!.body)
        }

        binding.ivBack.setOnClickListener{ saveNote() }
    }

    private fun saveNote() {
        val newTitle = binding.etTitle.text.toString()
        val newBody = binding.etBody.text.toString()

        val newNote: Note = if(note == null){
            Log.i("Hadesik", "New")
            Note(null, newTitle, newBody)
        }
        else{
            Log.i("Hadesik", "Modify: ${note!!.id} ${newTitle} ${newBody}")
            Note(note!!.id, newTitle, newBody)
        }

        val resultIntent = Intent().apply {
            if(newTitle.isNotEmpty() || newBody.isNotEmpty())
                putExtra("newNote", newNote)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}