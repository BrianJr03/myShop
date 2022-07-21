package jr.brian.myShop.model.remote.order

import jr.brian.myShop.model.remote.product.ProductItem

data class AddOrderInput(
    val bill_amount: Int,
    val delivery_address: DeliveryAddress,
    val items: ArrayList<Item>,
    val payment_method: String,
    val user_id: Int
)