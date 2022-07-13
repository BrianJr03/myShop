package jr.brian.myShop.presenter.sign_in_presenter

import android.view.View
import com.google.android.material.snackbar.Snackbar
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.model.remote.VolleyHelper

class SignInPresenter(
    private var volleyHelper: VolleyHelper,
    private val registrationView: SignInMVP.SignInView
) : SignInMVP.SignInPresenter {

    override fun signInUser(
        user: User,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        volleyHelper = VolleyHelper(view.context)
        volleyHelper.signInUser(user, object : OperationalCallback {
            override fun onSuccess(message: Any) {
                status = message as String
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                    startHomeActivity()
                }
            }

            override fun onFailure(message: String) {
                status = message
                    registrationView.apply {
                        onLoad(false)
                        setResult(message)
//                        showSnackbar(
//                            "Failed to login. Please check your email id or password.",
//                            view
//                        )
                    }
            }
        })
        return status
    }

    private fun showSnackbar(msg: String, view: View) {
        Snackbar.make(
            view.context,
            view,
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}