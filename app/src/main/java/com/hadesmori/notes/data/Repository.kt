package com.hadesmori.notes.data

import com.hadesmori.notes.data.database.dao.NoteDao
import com.hadesmori.notes.data.database.entities.NoteEntity
import com.hadesmori.notes.data.database.entities.toDatabase
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.domain.model.toDomain
import javax.inject.Inject

class Repository @Inject constructor(
    private val noteDao: NoteDao
){

    suspend fun getAllNotes(): List<Note>{
        val notes = noteDao.getAllNotes()
        return notes.map { it.toDomain() }
    }

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note.toDatabase())
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.id!!, note.title, note.body)
    }
}