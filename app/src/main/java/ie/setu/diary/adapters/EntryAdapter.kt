package ie.setu.diary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.diary.databinding.CardEntryBinding
import ie.setu.diary.models.DiaryModel

interface EntryListener {
    fun onEntryClick(entry: DiaryModel, position : Int)
    //fun onEntryClick(entry: DiaryModel)
    fun onbtnDeleteClick(entry: DiaryModel, position: Int)
}
class EntryAdapter(private var entries: List<DiaryModel>, private val listener: EntryListener) :
    RecyclerView.Adapter<EntryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEntryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val entry = entries[holder.adapterPosition]
        holder.bind(entry, listener)
    }

    override fun getItemCount(): Int = entries.size


    inner class MainHolder(private val binding: CardEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: DiaryModel, listener:EntryListener) {
            binding.entryTitle.text = entry.title
            binding.description.text = entry.description
            binding.date.text = entry.date.toString()
            binding.root.setOnClickListener { listener.onEntryClick(entry, adapterPosition) }
            Picasso.get().load(entry.image).resize(200,200).into(binding.imageIcon)
            binding.btnDelete.setOnClickListener{ listener.onEntryClick(entry, adapterPosition)}



        }
    }
}

