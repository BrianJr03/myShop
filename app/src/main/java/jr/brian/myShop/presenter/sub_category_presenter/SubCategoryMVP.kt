package jr.brian.myShop.presenter.sub_category_presenter

import jr.brian.myShop.model.remote.SubCategories

interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategories(categoryId: String)
    }

    interface SubCategoryView {
        fun setResult(subCategories: SubCategories?)
        fun onLoad(isLoading: Boolean)
    }
}