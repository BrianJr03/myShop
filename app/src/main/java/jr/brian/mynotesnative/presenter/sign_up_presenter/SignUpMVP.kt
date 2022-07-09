package jr.brian.mynotesnative.presenter.sign_up_presenter

import android.view.View

interface SignUpMVP {
    interface SignUpPresenter {
        fun signUpUser(
            email: String,
            password: String,
            view: View
        ): String
    }

    interface SignUpView {
        fun setResult(msg: String)
        fun onLoad(isLoading: Boolean)
        fun startHomeActivity()
    }
}