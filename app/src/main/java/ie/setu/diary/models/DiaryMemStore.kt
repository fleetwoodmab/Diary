package ie.setu.diary.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DiaryMemStore : DiaryStore {
    val entries = ArrayList<DiaryModel>()

    override fun findAll(): List<DiaryModel> {
        return entries
    }

    override fun create(entry: DiaryModel) {
        entry.id = getId()
        entries.add(entry)
        logAll()
    }

    override fun update(entry: DiaryModel) {
        var foundPlacemark: DiaryModel? = entries.find { p -> p.id == entry.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = entry.title
            foundPlacemark.description = entry.description
            logAll()
        }
    }

    fun logAll() {
        entries.forEach{ i("${it}") }
    }
}