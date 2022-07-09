package jr.brian.myShop.model.remote

interface OperationalCallback {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}