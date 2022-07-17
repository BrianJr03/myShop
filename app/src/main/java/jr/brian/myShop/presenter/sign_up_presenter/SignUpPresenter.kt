package jr.brian.myShop.presenter.sign_up_presenter

import android.view.View
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.user.User

class SignUpPresenter(
    private var sharedPrefHelper: SharedPrefHelper,
    private val registrationView: SignUpMVP.SignUpView
) : SignUpMVP.SignUpPresenter {

    override fun signUpUser(
        user: User,
        view: View
    ): String {
        var status = ""
        registrationView.onLoad(true)
        sharedPrefHelper = SharedPrefHelper(view.context)
        sharedPrefHelper.saveUserInDB(
            user,
            object : OperationalCallback {
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
                    }
                }
            })
        return status
    }
}