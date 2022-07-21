package jr.brian.myShop.model.local

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction { replace(containerId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun showSnackbar(str: String, view: View, id: Int) {
    Snackbar.make(
        view.context,
        view.findViewById(id),
        str,
        Snackbar.LENGTH_SHORT
    ).show()
}