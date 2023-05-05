package ie.setu.diary.activities

import android.content.Intent
import android.net.Uri
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    var entry = DiaryModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit = true

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp
        i("Diary Activity started...")


        if (intent.hasExtra("entry_edit")) {
            entry = intent.extras?.getParcelable("entry_edit")!!
            binding.entryTitle.setText(entry.title)
            binding.description.setText(entry.description)
            binding.date.setText(entry.date)
            binding.btnAdd.text=getString(R.string.button_editEntry)
            Picasso.get()
                .load(entry.image)
                .into(binding.entryImage)
            if (entry.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_entry_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            try {
                val date = LocalDate.parse(binding.date.text.toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                entry.date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
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
                Snackbar.make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        registerImagePickerCallback()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_entry, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.entries.delete(entry)
                finish()
            }        R.id.item_cancel -> { finish() }
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

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            entry.image = image

                            Picasso.get()
                                .load(entry.image)
                                .into(binding.entryImage)
                            binding.chooseImage.setText(R.string.change_entry_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }



}



