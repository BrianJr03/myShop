package jr.brian.myShop.model.remote.category

data class Inventory(
    val categories: ArrayList<Category>,
    val message: String,
    val status: Int
)