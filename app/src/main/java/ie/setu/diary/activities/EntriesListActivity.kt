package ie.setu.diary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.diary.R
import ie.setu.diary.main.MainApp

class EntriesListActivity : AppCompatActivity() {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entries_list)
        app = application as MainApp
    }
}