package jr.brian.myShop.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val category_id: String,
    val category_image_url: String,
    val category_name: String,
    val is_active: String
) : Parcelable