package com.hadesmori.notes.ui.notes


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadesmori.notes.databinding.FragmentNotesBinding
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.ui.detail.NoteDetailActivity
import com.hadesmori.notes.ui.notes.adapter.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var notesAdapter: NotesAdapter

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var startNoteDetailActivity: ActivityResultLauncher<Intent>

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel.getNotes()

        startNoteDetailActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val newNote = result.data?.serializable<Note>("newNote")
                newNote?.let { note ->
                    if(note.id != null){
                        noteViewModel.modifyNote(note)
                    }
                    else{
                        noteViewModel.addNote(note)
                    }

                    initUI()
                }
            }
        }

        initUI()
    }

    private fun initUI() {
        initList()
        initUIState()
    }


    private fun initList() {
        notesAdapter = NotesAdapter(onItemSelected = {
            modifyNote(it)
        })

        binding.addNoteButton.setOnClickListener { modifyNote() }

        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }
    }

    private fun modifyNote(note: Note? = null) {
        val intent = Intent(requireContext(), NoteDetailActivity::class.java)
        intent.putExtra("note", note)
        startNoteDetailActivity.launch(intent)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.noteModels.collect {
                    notesAdapter.updateList(it)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}