package jr.brian.myShop.model.remote.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    val average_rating: String,
    val category_id: String,
    val category_name: String,
    val description: String,
    var price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val sub_category_id: String,
    val subcategory_name: String,
    var qty: Int = 0,
    var total: Int = 0
) : Parcelable