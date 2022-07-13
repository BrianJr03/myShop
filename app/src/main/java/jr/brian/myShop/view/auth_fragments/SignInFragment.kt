package jr.brian.myShop.view.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jr.brian.myShop.R
import jr.brian.myShop.model.remote.Constant.SIGN_IN_TAG
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.model.remote.VolleyHelper
import jr.brian.myShop.presenter.sign_in_presenter.SignInMVP
import jr.brian.myShop.presenter.sign_in_presenter.SignInPresenter
import jr.brian.myShop.view.activities.HomeActivity

class SignInFragment : Fragment(), SignInMVP.SignInView {
    private lateinit var presenter: SignInMVP.SignInPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        presenter = SignInPresenter(VolleyHelper(view.context), this)
        val email = view.findViewById<EditText>(R.id.email_et_signIn).text
        val password = view.findViewById<EditText>(R.id.password_et_signIn).text
        val user = User(
            emailId = email.toString(),
            "",
            "",
            password = password.toString(),
            "")
        view.findViewById<Button>(R.id.signInBTN).setOnClickListener {
            if (email.isEmpty() || password.isEmpty()) {
                showSnackbar("Ensure fields are not empty")
            } else {
                (presenter as SignInPresenter).signInUser(user, view)
            }
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun setResult(msg: String) {
        Log.i(SIGN_IN_TAG, msg)
        showSnackbar(msg)
    }

    private fun showSnackbar(msg: String) {
        activity?.let {
            Snackbar.make(
                it.findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onLoad(isLoading: Boolean) {
        val cpb = view?.findViewById<ProgressBar>(R.id.progress_bar_signIn)
        if (isLoading) {
            cpb?.visibility = View.VISIBLE
        } else {
            cpb?.visibility = View.GONE
        }
    }

    override fun startHomeActivity() {
        ContextCompat.startActivity(
            requireContext(),
            Intent(context, HomeActivity::class.java),
            null
        )
    }
}