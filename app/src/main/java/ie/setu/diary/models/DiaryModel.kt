package ie.setu.diary.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class DiaryModel(var id: Long = 0, var title: String = "", var description: String = "") : Parcelable {
}