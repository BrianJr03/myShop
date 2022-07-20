package jr.brian.myShop.view.auth_fragments

import android.content.Intent
import android.os.Bundle
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
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.sign_in_presenter.SignInMVP
import jr.brian.myShop.presenter.sign_in_presenter.SignInPresenter
import jr.brian.myShop.view.activities.CategoryActivity
import org.json.JSONObject

class SignInFragment : Fragment(), SignInMVP.SignInView {
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var presenter: SignInMVP.SignInPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        presenter = SignInPresenter(VolleyHelper(view.context), this)
        sharedPrefHelper = SharedPrefHelper(requireContext())
        val email = view.findViewById<EditText>(R.id.email_et_signIn).text
        val password = view.findViewById<EditText>(R.id.password_et_signIn).text
        view.findViewById<Button>(R.id.signInBTN).setOnClickListener {
            if (email.isEmpty() || password.isEmpty()) {
                showEmptyFieldsMsg()
            } else {
                (presenter as SignInPresenter).signInUser(
                    email.toString(),
                    password.toString(),
                    view
                )
            }
        }
    }

    companion object {
        const val FILENAME = "login-details"
        const val USER_ID = "user_id"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun setResult(msg: Any) {
        val user = msg as JSONObject
        sharedPrefHelper.editor
            .putString(USER_ID, user.getString("user_id"))
    }

    private fun showEmptyFieldsMsg() {
        activity?.let {
            Snackbar.make(
                it.findViewById(android.R.id.content),
                "Ensure fields are not empty", Snackbar.LENGTH_LONG
            ).show()
        }
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
            Intent(context, CategoryActivity::class.java),
            null
        )
    }
}