package jr.brian.myShop.model.remote

data class Product(
    val message: String,
    val products: List<ProductItem>,
    val status: Int
)