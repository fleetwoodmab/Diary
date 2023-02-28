package ie.setu.diary.models

import timber.log.Timber.i
import ie.setu.diary.models.DiaryModel

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DiaryMemStore : DiaryStore {
    val entries = ArrayList<DiaryModel>()

    override fun findAll(): List<DiaryModel> {
        return entries
    }

    override fun create(placemark: DiaryModel) {
        placemark.id = getId()
        entries.add(placemark)
        logAll()
    }

    override fun update(entry: DiaryModel) {
        var foundEntry: DiaryModel? = entries.find { p -> p.id == entry.id }
        if (foundEntry != null) {
            foundEntry.title = entry.title
            foundEntry.description = entry.description
            logAll()
        }
    }

    fun logAll() {
        entries.forEach{ i("${it}") }
    }
}
