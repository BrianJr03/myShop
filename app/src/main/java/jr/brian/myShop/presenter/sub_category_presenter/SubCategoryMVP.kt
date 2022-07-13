package jr.brian.myShop.presenter.sub_category_presenter

import jr.brian.myShop.model.remote.Sub

interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategories(categoryId: String)
    }

    interface SubCategoryView {
        fun setResult(sub: Sub?)
        fun onLoad(isLoading: Boolean)
    }
}