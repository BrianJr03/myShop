package jr.brian.mynotesnative.presenter.sign_in_presenter

import android.view.View
import jr.brian.mynotesnative.model.remote.OperationalCallback
import jr.brian.mynotesnative.model.remote.VolleyHelper
import org.json.JSONObject

class SignInPresenter(
    private val volleyHandler: VolleyHelper,
    private val registrationView: SignInMVP.SignInView
) : SignInMVP.SignInPresenter {

    override fun signInUser(view: View, data: JSONObject): String {
        registrationView.onLoad(true)
        val message = volleyHandler.signInUser(view, data,
            object : OperationalCallback {
                override fun onSuccess(message: String) {
                    registrationView.apply {
                        onLoad(false)
                        setResult(message, view.context)
                        startNotesGridActivity()
                    }
                }

                override fun onFailure(message: String) {
                    registrationView.apply {
                        onLoad(false)
                        setResult(message, view.context)
                    }
                }
            })
        return message
    }
}