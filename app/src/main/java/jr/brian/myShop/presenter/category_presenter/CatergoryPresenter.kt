package jr.brian.myShop.presenter.category_presenter

import jr.brian.myShop.model.remote.Inventory
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.VolleyHelper

class CategoryPresenter(
    private val volleyHandler: VolleyHelper,
    private val loginView: CategoryMVP.CategoryView
) : CategoryMVP.CategoryPresenter {

    override fun getCategories() {
        loginView.onLoad(true)
        val message = volleyHandler.getCategories(
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(message as Inventory)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
    }
}