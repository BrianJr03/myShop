package jr.brian.mynotesnative.model.remote

interface OperationalCallback {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}