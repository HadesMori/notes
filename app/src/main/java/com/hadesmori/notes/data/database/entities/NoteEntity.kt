package com.hadesmori.notes.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hadesmori.notes.domain.model.Note

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,

    @ColumnInfo("title") val title: String,
    @ColumnInfo("body") val body: String,
)

fun Note.toDatabase() = NoteEntity(title = title, body = body)