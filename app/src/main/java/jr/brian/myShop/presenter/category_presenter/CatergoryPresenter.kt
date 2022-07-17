package jr.brian.myShop.presenter.category_presenter

import jr.brian.myShop.model.remote.category.Inventory
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.volley.VolleyHelper

class CategoryPresenter(
    private val volleyHelper: VolleyHelper,
    private val categoryView: CategoryMVP.CategoryView
) : CategoryMVP.CategoryPresenter {

    override fun getCategories() {
        categoryView.onLoad(true)
        volleyHelper.getCategories(
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    categoryView.onLoad(false)
                    categoryView.setResult(message as Inventory)
                }

                override fun onFailure(message: String) {
                    categoryView.onLoad(false)
                    categoryView.setResult(null)
                }
            })
    }
}