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
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days

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

    private fun format(noteDate: Date) : String {
        val currentTime = Date()

        val diffInMillis = currentTime.time - noteDate.time
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val noteYear = Calendar.getInstance().apply {
            time = noteDate
        }.get(Calendar.YEAR)

        return when {
            // If the note was created within the last 24 hours, show the time
            diffInDays < 1 -> {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(noteDate)
            }
            // If the note was created this year but more than a day ago, show day + month
            noteYear == currentYear -> {
                SimpleDateFormat("dd MMM", Locale.getDefault()).format(noteDate)
            }
            // If the note was created in a previous year, show day + month + year
            else -> {
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(noteDate)
            }
        }
    }
}