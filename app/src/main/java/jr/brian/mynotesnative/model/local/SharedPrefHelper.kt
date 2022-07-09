package jr.brian.mynotesnative.model.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.mynotesnative.model.remote.OperationalCallback
import jr.brian.mynotesnative.view.auth_fragments.SignUpFragment

class SharedPrefHelper(context: Context) {
    private var editor: SharedPreferences.Editor
    var encryptedSharedPrefs: SharedPreferences

    init {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        encryptedSharedPrefs = EncryptedSharedPreferences.create(
            SignUpFragment.FILENAME,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
        editor = encryptedSharedPrefs.edit()
    }

    fun saveUserInDB(email: String, password: String, callback: OperationalCallback) {
        editor.apply {
            putString(SignUpFragment.EMAIL, email)
            putString(SignUpFragment.PASSWORD, password)
            if (commit()) {
                callback.onSuccess("Success")
            } else callback.onFailure("Failure")
        }
    }

    fun verifySignIn(email: String, password: String, callback: OperationalCallback) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            callback.onSuccess("Success")
        } else {
            callback.onFailure("Failure")
        }
    }

    fun signOut() {
        editor.apply {
            clear()
            apply()
        }
    }
}