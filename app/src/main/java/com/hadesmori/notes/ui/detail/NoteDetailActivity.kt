package com.hadesmori.notes.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hadesmori.notes.R
import com.hadesmori.notes.databinding.ActivityNoteDetailBinding
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.ui.notes.NoteViewModel
import com.hadesmori.notes.ui.notes.NotesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.Date

@AndroidEntryPoint
class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding

    private val noteDetailViewModel: NoteDetailViewModel by viewModels()

    private var onDelete: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteDetailViewModel.getNoteDetail(intent.serializable<Note>(NotesFragment.NOTE_KEY)!!)
        initUI()
    }

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    private fun initUI() {
        updateUI()

        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                close()
            }
        })

        binding.ivBack.setOnClickListener{ close() }
        binding.ivDelete.setOnClickListener{ deleteNote() }
    }

    private fun updateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.noteModel.collect {
                    binding.etTitle.setText(it.title)
                    binding.etBody.setText(it.body)
                }
            }
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.confirm_to_delete_message)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ ->
                noteDetailViewModel.removeNote(noteDetailViewModel.noteModel.value)
                onDelete = true
                close()
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

        val noteId = noteDetailViewModel.noteModel.value.id
        val newNote = Note(noteId, newTitle, newBody, Date())

        if(newTitle.isNotEmpty() || newBody.isNotEmpty()){
            noteDetailViewModel.addNote(newNote)
        }
    }

    private fun close(){
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        if(!onDelete){
            saveNote()
        }
    }
}