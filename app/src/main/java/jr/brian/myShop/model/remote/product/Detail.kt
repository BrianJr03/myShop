package jr.brian.myShop.model.remote.product

data class Detail(
    val message: String,
    val product: ProductDetails,
    val status: Int
)