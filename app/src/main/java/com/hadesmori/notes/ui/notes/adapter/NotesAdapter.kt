package com.hadesmori.notes.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadesmori.notes.R
import com.hadesmori.notes.domain.model.Note

class NotesAdapter(
    private var notes: List<Note> = emptyList(),
    private val onItemSelected:(Note)-> Unit
) : RecyclerView.Adapter<NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.render(notes[position], onItemSelected)
    }

    override fun getItemCount(): Int = notes.size

    fun updateList(newList: List<Note>){
        notes = newList.sortedByDescending{ it.date }
        notifyDataSetChanged()
    }

    fun updateList(note: Note){
        notes.plus(note)
        notifyDataSetChanged()
    }
}