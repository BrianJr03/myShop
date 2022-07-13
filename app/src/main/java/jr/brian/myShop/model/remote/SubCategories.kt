package jr.brian.myShop.model.remote

data class SubCategories(
    val message: String,
    val status: Int,
    val subcategories: List<SubCategory>
)