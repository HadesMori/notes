package com.hadesmori.notes.domain.model

import androidx.room.PrimaryKey
import com.hadesmori.notes.data.database.entities.NoteEntity
import java.io.Serializable
import java.util.Date

data class Note(
    val id: Int?,
    val title: String = "",
    val body: String = "",
    val date: Date?
) : Serializable

fun NoteEntity.toDomain() : Note{
    return Note(id = id, title = title, body = body, date = date)
}