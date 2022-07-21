package jr.brian.myShop.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jr.brian.myShop.view.fragments.CartItemsFragment
import jr.brian.myShop.view.fragments.DeliveryFragment
import jr.brian.myShop.view.fragments.PaymentFragment
import jr.brian.myShop.view.fragments.SummaryFragment

class CheckOutViewPageAdapter(fragmentActivity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CartItemsFragment()
            1 -> DeliveryFragment()
            2 -> PaymentFragment()
            else -> SummaryFragment()
        }
    }
}