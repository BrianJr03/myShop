package jr.brian.myShop.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jr.brian.myShop.view.sub_category_fragments.SubCategoryOneFragment
import jr.brian.myShop.view.sub_category_fragments.SubCategoryThreeFragment
import jr.brian.myShop.view.sub_category_fragments.SubCategoryTwoFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = totalCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SubCategoryOneFragment()
            1 -> SubCategoryTwoFragment()
            else -> SubCategoryThreeFragment()
        }
    }

}