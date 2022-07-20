package jr.brian.myShop.presenter.address_presenter

import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.address.GetAddressesResponse
import jr.brian.myShop.model.remote.category.Inventory
import jr.brian.myShop.model.remote.volley.VolleyHelper

class AddressPresenter(
    private val volleyHelper: VolleyHelper,
    private val addressView: AddressMVP.AddressView
) : AddressMVP.AddressPresenter {

    override fun getAddresses(userId: String) {
        addressView.onLoad(true)
        volleyHelper.getAddresses(
            userId,
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    addressView.onLoad(false)
                    addressView.setResult(message as GetAddressesResponse, "get")
                }

                override fun onFailure(message: String) {
                    addressView.onLoad(false)
                    addressView.setResult(null, "")
                }
            })
    }

    override fun addAddress(userId: String, addrTitle: String, address: String) {
        addressView.onLoad(true)
        volleyHelper.addDeliveryAddress(
            userId,
            addrTitle,
            address,
            object : OperationalCallback {
                override fun onSuccess(message: Any) {
                    addressView.onLoad(false)
                    addressView.setResult(message as String, "add")
                }

                override fun onFailure(message: String) {
                    addressView.onLoad(false)
                    addressView.setResult(null, "")
                }
            })
    }
}