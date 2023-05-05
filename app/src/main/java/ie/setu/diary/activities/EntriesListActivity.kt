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
import ie.setu.diary.adapters.EntryListener
import ie.setu.diary.databinding.ActivityEntriesListBinding
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryJSONStore
import ie.setu.diary.models.DiaryModel

class EntriesListActivity : AppCompatActivity(), EntryListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityEntriesListBinding
    private var position: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EntryAdapter(app.entries.findAll(),this)
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
                notifyItemRangeChanged(0,app.entries.findAll().size)
            }
        }

    override fun onEntryClick(entry: DiaryModel, pos : Int) {
        val launcherIntent = Intent(this, DiaryActivity::class.java)
        launcherIntent.putExtra("entry_edit", entry)
        position = pos
        getClickResult.launch(launcherIntent)
    }
    override fun onbtnDeleteClick(entry: DiaryModel, position: Int) {
        app.entries.delete(entry)
        (binding.recyclerView.adapter)?.
        notifyItemRemoved(position)
        (binding.recyclerView.adapter)?.
        notifyItemRangeChanged(position, app.entries.findAll().size)
    }


    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.entries.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)     (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

}


