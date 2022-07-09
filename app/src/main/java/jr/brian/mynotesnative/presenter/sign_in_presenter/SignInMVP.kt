package jr.brian.mynotesnative.presenter.sign_in_presenter

import android.content.Context
import android.view.View
import org.json.JSONObject

interface SignInMVP {
    interface SignInPresenter {
        fun signInUser(view: View, data: JSONObject) : String
    }

    interface SignInView {
        fun setResult(msg: String, context: Context)
        fun onLoad(isLoading: Boolean)
        fun startNotesGridActivity()
    }
}