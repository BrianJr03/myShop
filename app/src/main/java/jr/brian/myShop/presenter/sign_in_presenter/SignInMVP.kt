package jr.brian.myShop.presenter.sign_in_presenter

import android.view.View

interface SignInMVP {
    interface SignInPresenter {
        fun signInUser(
            email: String,
            password: String,
            view: View
        ): String
    }

    interface SignInView {
        fun setResult(msg: Any)
        fun onLoad(isLoading: Boolean)
        fun startHomeActivity()
    }
}