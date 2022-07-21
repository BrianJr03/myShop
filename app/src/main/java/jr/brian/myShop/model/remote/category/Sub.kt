package jr.brian.myShop.model.remote.category

data class Sub(
    val message: String,
    val status: Int,
    val subcategories: ArrayList<SubCategory>
)