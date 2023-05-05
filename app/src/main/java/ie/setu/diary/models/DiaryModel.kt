package ie.setu.diary.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var image: Uri = Uri.EMPTY
) : Parcelable {}
