package jr.brian.myShop.model.remote

data class Inventory(
    val categories: ArrayList<Category>,
    val message: String,
    val status: Int
)