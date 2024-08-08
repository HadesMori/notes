package com.hadesmori.notes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadesmori.notes.domain.DeleteNote
import com.hadesmori.notes.domain.InsertNote

import com.hadesmori.notes.domain.UpdateNote
import com.hadesmori.notes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val insertNote: InsertNote,
    private val updateNote: UpdateNote,
    private val deleteNote: DeleteNote,
): ViewModel() {

    var noteModel = MutableStateFlow(Note(id = null, date = null))

    fun getNoteDetail(note: Note){
        noteModel.value = note
    }

    fun addNote(note: Note){
        viewModelScope.launch {
            if(note.id != null){
                noteModel.value = updateNote(note)
            }
            else{
                noteModel.value = insertNote(note)
            }
        }
    }

    fun removeNote(note: Note){
        viewModelScope.launch {
            deleteNote(note)
        }
    }
}