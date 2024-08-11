package com.hadesmori.notes.domain

import com.hadesmori.notes.data.Repository
import com.hadesmori.notes.domain.model.Note
import javax.inject.Inject

class GetAllNotes @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() : List<Note> = repository.getAllNotes()

}