package jr.brian.myShop.model.remote

interface OperationalCallback {
    fun onSuccess(message: Any)
    fun onFailure(message: String)
}