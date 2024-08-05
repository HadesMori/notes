package com.hadesmori.notes.domain.model

import androidx.room.PrimaryKey
import com.hadesmori.notes.data.database.entities.NoteEntity
import java.io.Serializable

data class Note(
    val id: Int?,
    val title: String = "",
    val body: String = "",
) : Serializable

fun NoteEntity.toDomain() = Note(id = id, title = title, body = body)