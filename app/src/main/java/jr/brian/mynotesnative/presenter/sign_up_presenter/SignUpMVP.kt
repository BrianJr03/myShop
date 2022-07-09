package jr.brian.mynotesnative.presenter.sign_up_presenter

import android.content.Context
import android.view.View
import jr.brian.mynotesnative.model.remote.OperationalCallback

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