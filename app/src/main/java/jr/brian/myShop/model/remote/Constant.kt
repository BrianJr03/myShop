package jr.brian.myShop.model.remote

object Constant {
    const val BASE_URL = "https://psmobitech.com/myshop/index.php/"
    const val BASE_IMAGE_URL = "https://psmobitech.com/myshop/images/"
    const val CATEGORY_EP = "Category"
    const val GET_SUB_CATEGORY_BY_ID_EP = "SubCategory?category_id="
    const val GET_PRODUCT_LIST_BY_SUB_CATEGORY_EP = "SubCategory/products/"
    const val GET_SEARCH_PRODUCT_EP = "Product/search?query="
    const val GET_PRODUCT_DETAILS_EP = "Product/details/"
    const val POST_ADD_DELIVERY_ADDR_EP = "User/address"
    const val GET_ALL_USER_ADDRESSES_EP = "User/addresses/"
    const val POST_PLACE_ORDER_EP = "/Order"
    const val GET_ALL_USER_ORDERS_EP = "Order/userOrders/"
    const val GET_ORDER_DETAILS_EP = "Order?order_id="

    const val SIGN_UP_EP = "User/register"
    const val SIGN_IN_EP = "User/auth"

    const val SIGN_UP_TAG = "SIGNUP"
    const val SIGN_IN_TAG = "SIGNIN"
    const val ERROR_TAG = "ERROR"
}