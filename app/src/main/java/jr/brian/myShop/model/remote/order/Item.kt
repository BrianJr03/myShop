package jr.brian.myShop.model.remote.order

data class Item(
    val product_id: Int,
    val quantity: Int,
    val unit_price: Int
)