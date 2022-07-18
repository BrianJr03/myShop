package jr.brian.myShop.presenter.product_details_presenter

import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.product.Detail
import jr.brian.myShop.model.remote.volley.VolleyHelper

class DetailsPresenter(
    private val volleyHelper: VolleyHelper,
    private val detailsView: DetailsMVP.DetailsView
) : DetailsMVP.CategoryPresenter {

    override fun getProductDetails(productId: String) {
        detailsView.onLoad(true)
        volleyHelper.getProductDetails(
            productId,
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    detailsView.onLoad(false)
                    detailsView.setResult(message as Detail)
                }

                override fun onFailure(message: String) {
                    detailsView.onLoad(false)
                    detailsView.setResult(null)
                }
            })
    }
}