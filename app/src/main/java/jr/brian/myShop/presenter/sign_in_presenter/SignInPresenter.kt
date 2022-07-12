package jr.brian.myShop.presenter.sign_in_presenter

import android.view.View
import com.google.android.material.snackbar.Snackbar
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.User

class SignInPresenter(
    private var sharedPrefHelper: SharedPrefHelper,
    private val registrationView: SignInMVP.SignInView
) : SignInMVP.SignInPresenter {

    override fun signInUser(
        user: User,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        sharedPrefHelper = SharedPrefHelper(view.context)
        sharedPrefHelper.verifySignIn(user, object : OperationalCallback {
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
                if (message == "Empty") {
                    showSnackbar("Ensure all fields aren't empty", view)
                } else {
                    registrationView.apply {
                        onLoad(false)
                        setResult(message)
                        showSnackbar("Account not found. Please sign up.", view)
                    }
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