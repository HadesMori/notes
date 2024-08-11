package com.hadesmori.notes.ui.notes


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadesmori.notes.databinding.FragmentNotesBinding
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.ui.detail.NoteDetailActivity
import com.hadesmori.notes.ui.notes.adapter.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date


@AndroidEntryPoint
class NotesFragment : Fragment() {

    companion object{
        const val NOTE_KEY = "note"
    }

    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var notesAdapter: NotesAdapter

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var startNoteDetailActivity: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startNoteDetailActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            updateUI()
        }

        updateUI()
    }

    private fun initUI() {
        initList()
        initUIState()
    }

    private fun updateUI(){
        noteViewModel.getNotes()
        initUI()
    }


    private fun initList() {
        notesAdapter = NotesAdapter(onItemSelected = {
            modifyNote(it)
        })

        binding.addNoteButton.setOnClickListener { modifyNote(Note(id = null, date = Date())) }

        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                noteViewModel.getByQuery(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                noteViewModel.getByQuery(query)
                return false
            }
        })
    }

    private fun modifyNote(note: Note) {
        val intent = Intent(requireContext(), NoteDetailActivity::class.java)
        intent.putExtra(NOTE_KEY, note)
        startNoteDetailActivity.launch(intent)
        binding.searchView.clearFocus()
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