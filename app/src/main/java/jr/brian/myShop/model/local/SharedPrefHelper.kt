package jr.brian.myShop.model.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.myShop.model.remote.user.User
import jr.brian.myShop.view.auth_fragments.SignUpFragment

class SharedPrefHelper(context: Context) {
    var editor: SharedPreferences.Editor
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

    fun saveUserInDB(user: User) {
        editor.apply {
            clear()
            apply()
            putString(SignUpFragment.FULL_NAME, user.fullName)
            putString(SignUpFragment.MOBILE_NO, user.mobileNo)
            putString(SignUpFragment.EMAIL, user.emailId)
            putString(SignUpFragment.PASSWORD, user.password)
            commit()
        }
    }

    fun signOut() {
        editor.apply {
            clear()
            apply()
        }
    }
}