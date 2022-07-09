package jr.brian.myShop.presenter.sign_up_presenter

import android.view.View
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.OperationalCallback

class SignUpPresenter(
    private var sharedPrefHelper: SharedPrefHelper,
    private val registrationView: SignUpMVP.SignUpView
) : SignUpMVP.SignUpPresenter {

    override fun signUpUser(email: String, password: String, view: View): String {
        var status = ""
        registrationView.onLoad(true)
        sharedPrefHelper = SharedPrefHelper(view.context)
        sharedPrefHelper.saveUserInDB(email, password, object : OperationalCallback {
            override fun onSuccess(message: String) {
                status = "Success"
                registrationView.apply {
                    onLoad(false)
                    setResult(message)
                    startHomeActivity()
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