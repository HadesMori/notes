package com.hadesmori.notes.domain

import com.hadesmori.notes.data.Repository
import com.hadesmori.notes.domain.model.Note
import javax.inject.Inject

class GetAllByQuery @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(query: String) : List<Note>{
        return repository.getAllByQuery(query)
    }
}