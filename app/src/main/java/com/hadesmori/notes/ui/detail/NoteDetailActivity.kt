package com.hadesmori.notes.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hadesmori.notes.R
import com.hadesmori.notes.databinding.ActivityNoteDetailBinding
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.ui.notes.NotesFragment
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
        note = intent.serializable<Note>(NotesFragment.NOTE_KEY)

        if(note != null){
            binding.etTitle.setText(note!!.title)
            binding.etBody.setText(note!!.body)
        }

        binding.ivBack.setOnClickListener{ saveNote() }
        binding.ivDelete.setOnClickListener{ deleteNote() }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.confirm_to_delete_message)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ ->
                val resultIntent = Intent().apply {
                    putExtra(NotesFragment.NOTE_TO_DELETE_KEY, note)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()
    }

    private fun saveNote() {
        val newTitle = binding.etTitle.text.toString()
        val newBody = binding.etBody.text.toString()

        val newNote: Note = if(note == null){
            Note(null, newTitle, newBody)
        }
        else{
            Note(note!!.id, newTitle, newBody)
        }

        val resultIntent = Intent().apply {
            if(newTitle.isNotEmpty() || newBody.isNotEmpty())
                putExtra(NotesFragment.NEW_NOTE_KEY, newNote)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}