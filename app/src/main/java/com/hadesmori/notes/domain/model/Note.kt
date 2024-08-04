package com.hadesmori.notes.domain.model

import com.hadesmori.notes.data.database.entities.NoteEntity
import java.io.Serializable

data class Note (
    val title: String = "",
    val body: String = "",
) : Serializable

fun NoteEntity.toDomain() = Note(title = title, body = body)