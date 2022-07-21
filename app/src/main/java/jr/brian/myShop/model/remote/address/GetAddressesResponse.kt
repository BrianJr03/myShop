package jr.brian.myShop.model.remote.address

data class GetAddressesResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)