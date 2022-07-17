package jr.brian.myShop.model.remote.category

import jr.brian.myShop.model.remote.category.Category

data class Inventory(
    val categories: ArrayList<Category>,
    val message: String,
    val status: Int
)