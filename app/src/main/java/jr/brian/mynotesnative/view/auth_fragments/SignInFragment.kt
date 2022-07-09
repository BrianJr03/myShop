package jr.brian.mynotesnative.view.auth_fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.mynotesnative.R
import jr.brian.mynotesnative.model.local.showSnackbar
import jr.brian.mynotesnative.presenter.sign_in_presenter.SignInMVP
import jr.brian.mynotesnative.view.note_activities.NotesGridActivity

class SignInFragment : Fragment(), SignInMVP.SignInView {
    private lateinit var encryptedSharedPrefs: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEncryptedPrefs(view.context)
        initView(view)
    }

    private fun initEncryptedPrefs(context: Context) {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        encryptedSharedPrefs = EncryptedSharedPreferences.create(
            FILENAME,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private fun initView(view: View) {
        view.findViewById<Button>(R.id.signInBTN).setOnClickListener {
            verifySignIn(view)
        }
    }

    private fun verifySignIn(view: View) {
        val emailEtSignIn = view.findViewById<EditText>(R.id.email_et_signIn)
        val passwordEtSignIn = view.findViewById<EditText>(R.id.password_et_signIn)
        if (emailEtSignIn.text.isNotEmpty() && passwordEtSignIn.text.isNotEmpty()) {
            if (encryptedSharedPrefs.contains(EMAIL) && encryptedSharedPrefs.contains(PASSWORD)) {
                startNotesGridActivity()
            } else {
                showSnackbar("Account not found. Please create an account", view)
            }
        } else {
            showSnackbar("Please ensure both fields aren't empty", view)
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

    override fun setResult(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoading: Boolean) {
        val cpb = view?.findViewById<ProgressBar>(R.id.progress_bar_signUp)
        if (isLoading) {
            cpb?.visibility = View.VISIBLE
        } else {
            cpb?.visibility = View.GONE
        }
    }

    override fun startNotesGridActivity() {
        ContextCompat.startActivity(
            requireContext(),
            Intent(context, NotesGridActivity::class.java),
            null
        )
    }
}