package jr.brian.myShop.presenter.product_details_presenter

import jr.brian.myShop.model.remote.product.Detail

interface DetailsMVP {
    interface CategoryPresenter {
        fun getProductDetails(productId: String)
    }

    interface DetailsView {
        fun setResult(detail: Detail?)
        fun onLoad(isLoading: Boolean)
    }
}