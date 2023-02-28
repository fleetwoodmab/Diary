package ie.setu.diary.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.diary.R
import ie.setu.diary.adapters.EntryAdapter
import ie.setu.diary.databinding.ActivityEntriesListBinding
import ie.setu.diary.main.MainApp

class EntriesListActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityEntriesListBinding
    private lateinit var adapter: EntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        adapter = EntryAdapter(app.entries)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : EntryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val launcherIntent = Intent(this@EntriesListActivity, DiaryActivity::class.java)
                launcherIntent.putExtra("position", position)
                startActivity(launcherIntent)
            }

            override fun notifyItemRemoved(adapterPosition: Int) {
                app.entries.removeAt(adapterPosition)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, DiaryActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.entries.size)
            }
        }
}


