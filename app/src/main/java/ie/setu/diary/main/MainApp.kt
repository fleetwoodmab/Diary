package ie.setu.diary.main

import android.app.Application
import ie.setu.diary.models.DiaryMemStore
import ie.setu.diary.models.DiaryModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val entries = DiaryMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Diary started")
        //entries.add(DiaryModel("yesterday", "water"))
        //entries.add(DiaryModel("today", "soda"))
        //entries.add(DiaryModel("tomorrow", "milk"))
    }
}