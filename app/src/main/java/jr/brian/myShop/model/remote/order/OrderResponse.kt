package jr.brian.myShop.model.remote.order

data class OrderResponse(
    val message: String,
    val order_id: Int,
    val status: Int
)