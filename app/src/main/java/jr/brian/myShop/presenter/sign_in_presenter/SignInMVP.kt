package jr.brian.myShop.presenter.sign_in_presenter

import android.view.View
import jr.brian.myShop.model.remote.user.User

interface SignInMVP {
    interface SignInPresenter {
        fun signInUser(
            user: User,
            view: View
        ): String
    }

    interface SignInView {
        fun setResult(msg: String)
        fun onLoad(isLoading: Boolean)
        fun startHomeActivity()
    }
}