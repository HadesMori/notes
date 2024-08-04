package com.hadesmori.notes.di

import android.content.Context
import androidx.room.Room
import com.hadesmori.notes.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val NOTE_DATABASE_NAME = "note_database"

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDatabase::class.java, NOTE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.getNoteDao()
}