package jr.brian.myShop.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val title: String,
    val body: String,
    val date: String,
    val passcode: String,
    val bodyFontSize: String = "14",
    val textColor: String,
    val isStarred: String,
    val isLocked: String,
    var index: Int = 0,
) : Parcelable