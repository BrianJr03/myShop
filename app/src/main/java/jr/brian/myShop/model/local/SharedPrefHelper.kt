package jr.brian.myShop.model.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.myShop.model.remote.OperationalCallback
import jr.brian.myShop.model.remote.User
import jr.brian.myShop.model.remote.VolleyHelper
import jr.brian.myShop.view.auth_fragments.SignInFragment
import jr.brian.myShop.view.auth_fragments.SignUpFragment

class SharedPrefHelper(context: Context) {
    private var editor: SharedPreferences.Editor
    var encryptedSharedPrefs: SharedPreferences
    private val volleyHelper = VolleyHelper(context)

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

    fun saveUserInDB(
        user: User,
        callback: OperationalCallback
    ) {
        editor.apply {
            putString(SignUpFragment.FULL_NAME, user.fullName)
            putString(SignUpFragment.MOBILE_NO, user.mobileNo)
            putString(SignUpFragment.EMAIL, user.emailId)
            putString(SignUpFragment.PASSWORD, user.password)
            if (commit()) {
                volleyHelper.signUpUser(user, callback)
                callback.onSuccess("Success")
            } else callback.onFailure("Failure")
        }
    }

    fun signOut() {
        editor.apply {
            clear()
            apply()
        }
    }
}