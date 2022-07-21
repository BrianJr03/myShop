package jr.brian.myShop.view.auth_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import jr.brian.myShop.R
import jr.brian.myShop.model.local.showSnackbar
import jr.brian.myShop.model.remote.Constant.SIGN_UP_TAG
import jr.brian.myShop.model.remote.user.User
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.sign_up_presenter.SignUpMVP
import jr.brian.myShop.presenter.sign_up_presenter.SignUpPresenter

class SignUpFragment : Fragment(), SignUpMVP.SignUpView {
    private lateinit var presenter: SignUpMVP.SignUpPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        presenter = SignUpPresenter(VolleyHelper(view.context), this)
        val signUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val fullName = view.findViewById<EditText>(R.id.fullName_et).text
        val mobileNo = view.findViewById<EditText>(R.id.mobileNo_et).text
        val email = view.findViewById<EditText>(R.id.email_et).text
        val password = view.findViewById<EditText>(R.id.password_et).text
        val cPassword = view.findViewById<EditText>(R.id.cPassword_et).text
        signUpBtn.setOnClickListener {
            if (email.toString().isNotEmpty()
                || password.toString().isNotEmpty()
                || cPassword.toString().isNotEmpty()
                || fullName.toString().isNotEmpty()
                || mobileNo.toString().isNotEmpty()
            ) {
                if (password.toString() == cPassword.toString()) {
                    val user = User(
                        email.toString(),
                        fullName.toString(),
                        mobileNo.toString(),
                        password.toString(),
                        ""
                    )
                    (presenter as SignUpPresenter).signUpUser(
                        user,
                        view
                    )
                } else showSnackbar("Passwords do not match", view, R.id.sign_up_root)
            } else showSnackbar("Ensure fields are not empty", view, R.id.sign_up_root)
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val FULL_NAME = "full name"
        const val MOBILE_NO = "mobile no"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    override fun setResult(msg: String) {
        Log.i(SIGN_UP_TAG, msg)
    }

    override fun onLoad(isLoading: Boolean) {
        val animationView =
            view?.findViewById<LottieAnimationView>(R.id.animation_view)
        if (isLoading) {
            animationView?.visibility = View.VISIBLE
        } else {
            animationView?.visibility = View.GONE
        }
    }

    override fun clear() {
        view?.apply {
            findViewById<EditText>(R.id.fullName_et)?.text?.clear()
            findViewById<EditText>(R.id.mobileNo_et)?.text?.clear()
            findViewById<EditText>(R.id.email_et)?.text?.clear()
            findViewById<EditText>(R.id.password_et)?.text?.clear()
            findViewById<EditText>(R.id.cPassword_et)?.text?.clear()
        }
    }
}