package com.hadesmori.notes.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.hadesmori.notes.data.database.dao.NoteDao
import com.hadesmori.notes.data.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao
}