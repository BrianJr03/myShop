package jr.brian.myShop.presenter.address_presenter

interface AddressMVP {
    interface AddressPresenter {
        fun getAddresses(userId: String)
        fun addAddress(userId: String, addrTitle: String, address: String)
    }

    interface AddressView {
        fun setResult(message: Any?, type: String)
        fun onLoad(isLoading: Boolean)
    }
}