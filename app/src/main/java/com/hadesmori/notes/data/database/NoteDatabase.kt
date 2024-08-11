package com.hadesmori.notes.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hadesmori.notes.data.database.converters.DateConverters
import com.hadesmori.notes.data.database.dao.NoteDao
import com.hadesmori.notes.data.database.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(DateConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao
}