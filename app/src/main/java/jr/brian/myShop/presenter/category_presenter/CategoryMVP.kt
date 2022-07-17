package jr.brian.myShop.presenter.category_presenter

import jr.brian.myShop.model.remote.category.Inventory

interface CategoryMVP {
    interface CategoryPresenter {
        fun getCategories()
    }

    interface CategoryView {
        fun setResult(inventory: Inventory?)
        fun onLoad(isLoading: Boolean)
    }
}