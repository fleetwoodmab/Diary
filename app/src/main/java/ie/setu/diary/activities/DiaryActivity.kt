package ie.setu.diary.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.diary.R
import ie.setu.diary.databinding.ActivityDiaryBinding
import ie.setu.diary.helpers.showImagePicker
import ie.setu.diary.main.MainApp
import ie.setu.diary.models.DiaryModel
import timber.log.Timber.i
import java.text.ParseException
import java.util.*

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    var entry = DiaryModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp
        i("Diary Activity started...")

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.UK)



        if (intent.hasExtra("entry_edit")) {
            entry = intent.extras?.getParcelable("entry_edit")!!
            binding.entryTitle.setText(entry.title)
            binding.description.setText(entry.description)
            binding.btnAdd.text=getString(R.string.button_editEntry)
            Picasso.get()
                .load(entry.image)
                .into(binding.entryImage)
        }

        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            try {
                val date = dateFormat.parse(binding.date.text.toString())
                entry.date = date
            } catch (e: ParseException) {
                Snackbar.make(it,"Invalid date format. Please enter a date in the format dd/MMM/yyyy", Snackbar.LENGTH_LONG)
                    .show()
            }
            if (entry.title.isNotEmpty()) {
                if(intent.hasExtra("entry_edit")){
                    app.entries.update(entry.copy())
                }
                else  app.entries.create(entry.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()


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


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            entry.image = result.data!!.data!!
                            Picasso.get()
                                .load(entry.image)
                                .into(binding.entryImage)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}



