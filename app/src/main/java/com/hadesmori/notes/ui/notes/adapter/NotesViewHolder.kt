package com.hadesmori.notes.ui.notes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hadesmori.notes.databinding.ItemNoteBinding
import com.hadesmori.notes.domain.model.Note

class NotesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNoteBinding.bind(itemView)

    fun render(note: Note, onItemSelected: (Note) -> Unit){
        binding.tvTitle.text = note.title
        binding.tvBody.text = note.body
        binding.cvNote.setOnClickListener { onItemSelected(note) }
    }
}