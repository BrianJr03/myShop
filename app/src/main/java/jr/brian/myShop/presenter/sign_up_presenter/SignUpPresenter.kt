package jr.brian.myShop.presenter.sign_up_presenter

import android.view.View
import jr.brian.myShop.R
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.local.showSnackbar
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.user.User
import jr.brian.myShop.model.remote.volley.VolleyHelper

class SignUpPresenter(
    private var volleyHelper: VolleyHelper,
    private val registrationView: SignUpMVP.SignUpView
) : SignUpMVP.SignUpPresenter {

    override fun signUpUser(
        user: User,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        volleyHelper = VolleyHelper(view.context)
        volleyHelper.signUpUser(user, object : OperationalCallback {
            override fun onSuccess(message: Any) {
                SharedPrefHelper(view.context).saveUserInDB(user)
                status = message as String
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                    clear()
                    showSnackbar("Account created. Please sign in", view, R.id.sign_up_root)
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