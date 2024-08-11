package com.hadesmori.notes.domain

import com.hadesmori.notes.data.Repository
import com.hadesmori.notes.domain.model.Note
import javax.inject.Inject

class UpdateNote @Inject constructor(
    private val repository: Repository
)
{
    suspend operator fun invoke(note: Note) : Note{
        return repository.updateNote(note)
    }
}