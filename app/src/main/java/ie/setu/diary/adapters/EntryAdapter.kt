package ie.setu.diary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.diary.databinding.CardEntryBinding
import ie.setu.diary.models.DiaryModel

class EntryAdapter constructor(private var entries: List<DiaryModel>) :
        RecyclerView.Adapter<EntryAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardEntryBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val entry = entries[holder.adapterPosition]
            holder.bind(entry)
        }

        override fun getItemCount(): Int = entries.size

        class MainHolder(private val binding: CardEntryBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(entry: DiaryModel) {
                binding.entryTitle.text = entry.title
                binding.description.text = entry.description
            }
        }
    }