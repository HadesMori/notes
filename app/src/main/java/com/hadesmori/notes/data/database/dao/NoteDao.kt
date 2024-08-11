package com.hadesmori.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hadesmori.notes.data.database.entities.NoteEntity
import com.hadesmori.notes.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNote(id: Long) : NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity) : Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("UPDATE note_table SET title = :title, body = :body , date = :date WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, body: String, date: Date)

    @Query("SELECT * FROM note_table WHERE title LIKE :query OR body LIKE :query")
    suspend fun getAllByQuery(query: String) : List<NoteEntity>
}