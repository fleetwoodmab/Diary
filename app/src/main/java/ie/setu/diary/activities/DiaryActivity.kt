package ie.setu.diary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.diary.databinding.ActivityDiaryBinding
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryModel
import timber.log.Timber
import timber.log.Timber.i

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    var entry = DiaryModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Diary Activity started...")

        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            if (entry.title.isNotEmpty()) {
                app.entries.add(entry.copy())
                i("add Button Pressed: $entry")
                for (i in app.entries.indices)
                { i("Placemark[$i]:${this.app.entries[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}

