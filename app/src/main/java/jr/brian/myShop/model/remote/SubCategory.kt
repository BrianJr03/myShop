package jr.brian.myShop.model.remote

data class SubCategory(
    val category_id: String,
    val is_active: String,
    val subcategory_id: String,
    val subcategory_image_url: String,
    val subcategory_name: String
)