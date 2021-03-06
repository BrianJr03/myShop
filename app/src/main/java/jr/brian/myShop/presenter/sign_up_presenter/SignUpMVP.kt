package jr.brian.myShop.presenter.sign_up_presenter

import android.view.View
import jr.brian.myShop.model.remote.user.User

interface SignUpMVP {
    interface SignUpPresenter {
        fun signUpUser(
            user: User,
            view: View
        ): String
    }

    interface SignUpView {
        fun setResult(msg: String)
        fun onLoad(isLoading: Boolean)
        fun clear()
    }
}