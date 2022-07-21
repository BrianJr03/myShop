package jr.brian.myShop.model.remote.product

data class Product(
    val message: String,
    val products: List<ProductItem>,
    val status: Int
)