package jr.brian.mynotesnative.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var emailId: String,
    var fullName: String,
    var mobileNo: String,
    var password: String
) : Parcelable