package com.hadesmori.notes.ui.detail

import androidx.lifecycle.MutableLiveData
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
    private val deleteNote: DeleteNote,
    private val updateNote: UpdateNote,
): ViewModel() {

    var noteModel = MutableStateFlow(Note(null))

    fun getNoteDetail(note: Note){
        noteModel.value = note
    }

    fun addNote(note: Note){
        viewModelScope.launch {
            if(note.id != null){
                updateNote(note)
            }
            else{
                insertNote(note)
            }
        }
    }

    fun removeNote(note: Note){
        viewModelScope.launch {
            deleteNote(note)
            //noteModel.value = Note(null)
        }
    }
}