package ie.setu.diary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.diary.R
import ie.setu.diary.databinding.ActivityDiaryBinding
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryModel
import timber.log.Timber.i

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    var entry = DiaryModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp
        i("Diary Activity started...")

        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            if (entry.title.isNotEmpty()) {
                app.entries.add(entry.copy())
                i("add Button Pressed: $entry")
                for (i in app.entries.indices) {
                    i("Entry[$i]:${this.app.entries[i]}")
                }
            setResult(RESULT_OK)
            finish()
        }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_entry, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

