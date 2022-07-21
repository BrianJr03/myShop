package jr.brian.myShop.presenter.order_presenter

import jr.brian.myShop.model.remote.order.AddOrderInput

interface OrderMVP {
    interface OrderPresenter {
        fun placeOrder(addOrderInput: AddOrderInput)
    }

    interface OrderView {
        fun setResult(message: Any?)
        fun onLoad(isLoading: Boolean)
    }
}