package jr.brian.myShop.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import jr.brian.myShop.R
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Constant.SIGN_UP_TAG
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.presenter.sign_up_presenter.SignUpMVP
import jr.brian.myShop.presenter.sign_up_presenter.SignUpPresenter
import jr.brian.myShop.view.activities.CategoryActivity

class SignUpFragment : Fragment(), SignUpMVP.SignUpView {
    private lateinit var intent: Intent
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
        intent = Intent(view.context, CategoryActivity::class.java)
    }

    private fun initView(view: View) {
        presenter = SignUpPresenter(SharedPrefHelper(view.context), this)
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
                    intent.putExtra("USER", user)
                    (presenter as SignUpPresenter).signUpUser(
                        user,
                        view
                    )
                } else showSnackbar("Passwords do not match", view)
            } else showSnackbar("Ensure fields are not empty", view)
        }
    }

    private fun showSnackbar(str: String, view: View) {
        Snackbar.make(
            view.context,
            view.findViewById(R.id.sign_up_root),
            str,
            Snackbar.LENGTH_SHORT
        ).show()
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

    override fun startHomeActivity() {
        ContextCompat.startActivity(
            requireContext(),
            intent,
            null
        )
    }
}