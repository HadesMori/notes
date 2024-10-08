package com.hadesmori.notes.data

import android.util.Log
import com.hadesmori.notes.data.database.dao.NoteDao
import com.hadesmori.notes.data.database.entities.NoteEntity
import com.hadesmori.notes.data.database.entities.toDatabase
import com.hadesmori.notes.domain.model.Note
import com.hadesmori.notes.domain.model.toDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val noteDao: NoteDao
){

    suspend fun getAllNotes(): List<Note>{
        val notes = noteDao.getAllNotes()
        return notes.map { it.toDomain() }
    }

    suspend fun insertNote(note: Note) : Note{
        val databaseNote = note.toDatabase()
        return noteDao.getNote(noteDao.insertNote(databaseNote)).toDomain()
    }

    suspend fun updateNote(note: Note) : Note{
        val databaseNote = note.toDatabase()
        noteDao.updateNote(databaseNote.id, databaseNote.title, databaseNote.body, databaseNote.date!!)
        return databaseNote.toDomain()
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toDatabase())
    }

    suspend fun getAllByQuery(query: String) : List<Note>{
        val searchQuery = "%$query%"
        return noteDao.getAllByQuery(searchQuery).map { it.toDomain() }
    }
}