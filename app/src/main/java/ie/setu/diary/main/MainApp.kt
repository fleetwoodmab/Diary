package ie.setu.diary.main

import android.app.Application
import ie.setu.diary.models.DiaryModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val entries = ArrayList<DiaryModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Diary started")
    }
}