package com.hadesmori.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hadesmori.notes.data.database.entities.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)
}