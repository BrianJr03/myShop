package jr.brian.mynotesnative.model.local

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import jr.brian.mynotesnative.R

//fun AppCompatActivity.addFragment(containerId: Int, fragment: Fragment) {
//    supportFragmentManager.inTransaction { add(containerId, fragment) }
//}

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction { replace(containerId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun showSnackbar(str: String, view: View) {
    Snackbar.make(
        view.context,
        view.findViewById(R.id.sign_in_root),
        str,
        Snackbar.LENGTH_SHORT
    ).show()
}