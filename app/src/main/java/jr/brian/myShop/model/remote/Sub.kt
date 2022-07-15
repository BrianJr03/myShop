package jr.brian.myShop.model.remote

data class Sub(
    val message: String,
    val status: Int,
    val subcategories: ArrayList<SubCategory>
)