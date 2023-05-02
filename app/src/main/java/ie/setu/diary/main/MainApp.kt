package ie.setu.diary.main

import android.app.Application
import ie.setu.diary.models.DiaryJSONStore
import ie.setu.diary.models.DiaryModel
import ie.setu.diary.models.DiaryMemStore
import ie.setu.diary.models.DiaryStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var entries: DiaryStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        entries = DiaryJSONStore(applicationContext)
        i("Diary started")
    }


}