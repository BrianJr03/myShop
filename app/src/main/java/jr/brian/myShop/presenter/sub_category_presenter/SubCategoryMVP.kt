package jr.brian.myShop.presenter.sub_category_presenter


interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategories(categoryId: String)
        fun loadSubCategoryProducts(subCategoryId: String)
    }

    interface SubCategoryView {
        fun setResult(sub: Any?)
        fun onLoad(isLoading: Boolean)
    }
}