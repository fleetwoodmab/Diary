package ie.setu.diary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.diary.R
import ie.setu.diary.databinding.ActivityDiaryBinding
import ie.setu.diary.databinding.ActivityEntriesListBinding
import ie.setu.diary.databinding.CardEntryBinding
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryModel

class EntriesListActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityEntriesListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EntryAdapter(app.entries)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

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

    class MainHolder(private val binding : CardEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: DiaryModel) {
            binding.entryTitle.text = entry.title
            binding.description.text = entry.description
        }
    }
}