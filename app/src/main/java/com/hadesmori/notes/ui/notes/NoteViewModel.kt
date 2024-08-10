package com.hadesmori.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadesmori.notes.domain.GetAllByQuery
import com.hadesmori.notes.domain.GetAllNotes
import com.hadesmori.notes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotes: GetAllNotes,
    private val getAllByQuery: GetAllByQuery,
) : ViewModel(){

    val noteModels = MutableStateFlow<List<Note>>(emptyList())

    fun getNotes(){
        viewModelScope.launch {
            noteModels.value = getAllNotes()
        }
    }

    fun getByQuery(query: String){
        viewModelScope.launch {
            noteModels.value = getAllByQuery(query)
        }
    }
}