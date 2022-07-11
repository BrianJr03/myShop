package jr.brian.myShop.presenter.sign_up_presenter

import android.view.View
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.OperationalCallback

class SignUpPresenter(
    private var sharedPrefHelper: SharedPrefHelper,
    private val registrationView: SignUpMVP.SignUpView
) : SignUpMVP.SignUpPresenter {

    override fun signUpUser(
        fullName: String,
        mobileNo: String,
        email: String,
        password: String,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        sharedPrefHelper = SharedPrefHelper(view.context)
        sharedPrefHelper.saveUserInDB(
            fullName,
            mobileNo,
            email,
            password,
            object : OperationalCallback {
                override fun onSuccess(message: String) {
                    status = message
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
                    }
                }
            })
        return status
    }
}