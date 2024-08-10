package com.hadesmori.notes.ui.notes.adapter

import android.view.View
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.hadesmori.notes.R
import com.hadesmori.notes.databinding.ItemNoteBinding
import com.hadesmori.notes.domain.model.Note
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNoteBinding.bind(itemView)

    fun render(note: Note, onItemSelected: (Note) -> Unit){
        binding.tvTitle.text = note.title
        if(note.body.isNotEmpty()){
            binding.tvBody.text = note.body
        }
        else{
            binding.tvBody.text = getString(itemView.context, R.string.empty_body_item)
        }
        binding.tvDate.text = format(note.date!!)
        binding.cvNote.setOnClickListener { onItemSelected(note) }
    }

    private fun format(noteDate: Date): String {
        val currentCalendar = Calendar.getInstance()
        val noteCalendar = Calendar.getInstance().apply {
            time = noteDate
        }

        return when {
            // If the note was created today, show the time
            isSameDay(currentCalendar, noteCalendar) -> {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(noteDate)
            }
            // If the note was created this year but not today, show day + month
            noteCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) -> {
                SimpleDateFormat("dd MMM", Locale.getDefault()).format(noteDate)
            }
            // If the note was created in a previous year, show day + month + year
            else -> {
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(noteDate)
            }
        }
    }

    private fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }
}