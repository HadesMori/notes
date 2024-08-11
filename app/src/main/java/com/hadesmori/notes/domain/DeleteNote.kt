package com.hadesmori.notes.domain

import com.hadesmori.notes.data.Repository
import com.hadesmori.notes.domain.model.Note
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}