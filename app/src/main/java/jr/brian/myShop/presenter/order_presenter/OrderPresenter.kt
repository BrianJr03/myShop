package jr.brian.myShop.presenter.order_presenter

import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.order.AddOrderInput
import jr.brian.myShop.model.remote.order.OrderResponse
import jr.brian.myShop.model.remote.volley.VolleyHelper

class OrderPresenter(
    private val volleyHelper: VolleyHelper,
    private val orderView: OrderMVP.OrderView
) : OrderMVP.OrderPresenter {

    override fun placeOrder(addOrderInput: AddOrderInput) {
        orderView.onLoad(true)
        volleyHelper.placeOrder(
            addOrderInput,
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    orderView.onLoad(false)
                    orderView.setResult(message as OrderResponse)
                }

                override fun onFailure(message: String) {
                    orderView.onLoad(false)
                    orderView.setResult(message)
                }
            })
    }
}