package ie.setu.diary.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.diary.databinding.CardEntryBinding
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryModel


class EntryAdapter(private var entries: List<DiaryModel>) :
    RecyclerView.Adapter<EntryAdapter.MainHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun notifyItemRemoved(adapterPosition: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

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

    fun removeEntry(position: Int) {
        Log.d("EntryAdapter", "Before remove: ${entries.size}")
        entries = entries.toMutableList().apply { removeAt(position) }
        Log.d("EntryAdapter", "After remove: ${entries.size}")
        notifyItemRemoved(position)
        if (entries.isNullOrEmpty() || position < 0 || position >= entries.size) {
            return // do nothing if entries is null, empty or position is invalid
        }
        entries = entries.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
    }


    inner class MainHolder(private val binding: CardEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: DiaryModel) {
            binding.entryTitle.text = entry.title
            binding.description.text = entry.description

            binding.btnDelete.setOnClickListener {
                (binding.root.context.applicationContext as MainApp)
                    .entries.removeAt(adapterPosition)
                removeEntry(adapterPosition)
            }

            binding.root.setOnLongClickListener {
                (binding.root.context.applicationContext as MainApp)
                    .entries.removeAt(adapterPosition)
                removeEntry(adapterPosition)
                true
            }
        }
    }
}

