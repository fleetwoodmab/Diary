package ie.setu.diary.models

interface DiaryStore {
    fun findAll(): List<DiaryModel>
    fun create(entry: DiaryModel)
    fun update(entry: DiaryModel)
    fun delete(entry: DiaryModel)
}