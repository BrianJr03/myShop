package jr.brian.myShop.view.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityLandingBinding
import jr.brian.myShop.model.local.replaceFragment
import jr.brian.myShop.view.auth_fragments.SignInFragment
import jr.brian.myShop.view.auth_fragments.SignUpFragment
import jr.brian.myShop.view.activities.CategoryActivity

class LandingActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: ActivityLandingBinding
    private lateinit var encryptedSharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initEncryptedPrefs()
        initListeners()
        verifySignIn()
        replaceFragment(R.id.container, SignInFragment())
    }

    override fun onCheckedChanged(group: RadioGroup, checkId: Int) {
        val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)
        checkRadioButton?.let {
            when (checkRadioButton.id) {
                R.id.signin_rb -> replaceFragment(R.id.container, SignInFragment())
                else -> replaceFragment(R.id.container, SignUpFragment())
            }
        }
    }

    private fun initEncryptedPrefs() {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        encryptedSharedPrefs = EncryptedSharedPreferences.create(
            SignInFragment.FILENAME,
            mainKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private fun initListeners() {
        val group = findViewById<RadioGroup>(R.id.radio_group)
        group.setOnCheckedChangeListener(this)
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, CategoryActivity::class.java))
    }

    private fun verifySignIn() {
        if (encryptedSharedPrefs.contains(SignInFragment.EMAIL)
            && encryptedSharedPrefs.contains(
                SignInFragment.PASSWORD
            )
        ) {
            startHomeActivity()
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(a)
    }
}