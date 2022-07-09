package jr.brian.mynotesnative.presenter.sign_in_presenter

import android.view.View
import com.google.android.material.snackbar.Snackbar
import jr.brian.mynotesnative.model.local.SharedPrefHelper
import jr.brian.mynotesnative.model.remote.OperationalCallback
import jr.brian.mynotesnative.view.auth_fragments.SignInFragment

class SignInPresenter(
    private var sharedPrefHelper: SharedPrefHelper,
    private val registrationView: SignInMVP.SignInView
) : SignInMVP.SignInPresenter {

    override fun signInUser(email: String, password: String, view: View): String {
        var status = ""
        registrationView.onLoad(true)
        sharedPrefHelper = SharedPrefHelper(view.context)
        sharedPrefHelper.verifySignIn(email, password, object : OperationalCallback {
            override fun onSuccess(message: String) {
                status = "Success"
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                    sharedPrefHelper.apply {
                        if (encryptedSharedPrefs.contains(SignInFragment.EMAIL)
                            && encryptedSharedPrefs.contains(SignInFragment.PASSWORD)
                        ) {
                            startHomeActivity()
                        } else {
                            Snackbar.make(
                                view.context,
                                view,
                                "Account not found. Please sign up",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(message: String) {
                status = "Failure"
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                }
            }
        })
        return status
    }
}