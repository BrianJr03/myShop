package jr.brian.mynotesnative.view.auth_fragments

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
import jr.brian.mynotesnative.R
import jr.brian.mynotesnative.model.local.SharedPrefHelper
import jr.brian.mynotesnative.model.remote.User
import jr.brian.mynotesnative.presenter.sign_up_presenter.SignUpMVP
import jr.brian.mynotesnative.presenter.sign_up_presenter.SignUpPresenter
import jr.brian.mynotesnative.view.note_activities.NotesGridActivity

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
    }

    private fun initView(view: View) {
        presenter = SignUpPresenter(SharedPrefHelper(view.context), this)
        val signUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val emailEt = view.findViewById<EditText>(R.id.email_et)
        val passwordEt = view.findViewById<EditText>(R.id.password_et)
        val cPasswordEt = view.findViewById<EditText>(R.id.cPassword_et)
        signUpBtn.setOnClickListener {
            if (emailEt.text.isNotEmpty()
                || passwordEt.text.isNotEmpty()
                || cPasswordEt.text.isNotEmpty()
            ) {
                if (passwordEt.text.toString() == cPasswordEt.text.toString()) {
                    intent = Intent(view.context, NotesGridActivity::class.java)
                    intent.putExtra(
                        USER, User(
                            emailId = emailEt.text.toString(),
                            password = passwordEt.text.toString(),
                            fullName = "Brian Jr",
                            mobileNo = "2123223432"
                        )
                    )
                    presenter.signUpUser(
                        emailEt.text.toString(),
                        passwordEt.text.toString(),
                        view
                    )
                } else showSnackbar("Passwords do not match", view)
            } else showSnackbar("Please ensure all fields aren't empty", view)
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
        const val USER = "user"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    override fun setResult(msg: String) {
        Log.i("RESULT", msg)
    }

    override fun onLoad(isLoading: Boolean) {
        val cpb = view?.findViewById<ProgressBar>(R.id.progress_bar_signUp)
        if (isLoading) {
            cpb?.visibility = View.VISIBLE
        } else {
            cpb?.visibility = View.GONE
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