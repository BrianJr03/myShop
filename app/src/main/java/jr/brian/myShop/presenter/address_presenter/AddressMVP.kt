package jr.brian.myShop.presenter.address_presenter

interface AddressMVP {
    interface AddressPresenter {
        fun getAddresses()
        fun addAddress(userId: String, addrTitle: String, address: String)
    }

    interface AddressView {
        fun setResult(message: Any?)
        fun onLoad(isLoading: Boolean)
    }
}