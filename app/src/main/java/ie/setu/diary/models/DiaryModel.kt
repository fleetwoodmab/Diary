package ie.setu.diary.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class DiaryModel(var id: Long = 0, var title: String = "", var description: String = "", var date: Date=Date(2023,1, 1)) : Parcelable {
}