package ie.setu.diary.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DiaryMemStore : DiaryStore {
    val entries = ArrayList<DiaryModel>()

    fun logAll() {
        entries.forEach{ i("${it}") }
    }

    override fun findAll(): List<DiaryModel> {
        return entries
    }

    override fun create(entry: DiaryModel) {
        entry.id = getId()
        entries.add(entry)
        logAll()
    }

    override fun update(entry: DiaryModel) {
        var foundEntry: DiaryModel? = entries.find { p -> p.id == entry.id }
        if (foundEntry != null) {
            foundEntry.title = entry.title
            foundEntry.description = entry.description
            foundEntry.date = entry.date
            foundEntry.image = entry.image
            logAll()
        }
    }
    /*Word done here*/
    override fun delete(entry: DiaryModel){
        var foundEntry: DiaryModel? = entries.find { p -> p.id == entry.id }
        if (foundEntry != null) {
            entries.remove(foundEntry)
        }
    }
}


