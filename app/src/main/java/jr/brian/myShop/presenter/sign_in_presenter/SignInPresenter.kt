package jr.brian.myShop.presenter.sign_in_presenter

import android.view.View
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.volley.VolleyHelper
import org.json.JSONObject

class SignInPresenter(
    private var volleyHelper: VolleyHelper,
    private val registrationView: SignInMVP.SignInView
) : SignInMVP.SignInPresenter {

    override fun signInUser(
        email: String,
        password: String,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        volleyHelper = VolleyHelper(view.context)
        volleyHelper.signInUser(email, password, object : OperationalCallback {
            override fun onSuccess(message: Any) {
                val obj = message as JSONObject
                registrationView.apply {
                    onLoad(false)
                    setResult(obj)
                    startHomeActivity()
                }
            }

            override fun onFailure(message: String) {
                status = message
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                }
            }
        })
        return status
    }
}