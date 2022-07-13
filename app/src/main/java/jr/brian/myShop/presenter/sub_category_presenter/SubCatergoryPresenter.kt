package jr.brian.myShop.presenter.sub_category_presenter

import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.Sub
import jr.brian.myShop.model.remote.VolleyHelper

class SubCategoryPresenter(
    private val volleyHelper: VolleyHelper,
    private val subCategoryView: SubCategoryMVP.SubCategoryView
) : SubCategoryMVP.SubCategoryPresenter {

    override fun getSubCategories(categoryId: String) {
        subCategoryView.onLoad(true)
        volleyHelper.getSubCategories(
            categoryId,
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    subCategoryView.onLoad(false)
                    subCategoryView.setResult(message as Sub)
                }

                override fun onFailure(message: String) {
                    subCategoryView.onLoad(false)
                    subCategoryView.setResult(null)
                }
            })
    }
}