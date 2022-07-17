package jr.brian.myShop.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY_KEY
import jr.brian.myShop.model.remote.SubCategory
import jr.brian.myShop.view.sub_category_fragments.SubCategoryFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val subCategoryList: List<SubCategory>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = subCategoryList.size

    override fun createFragment(position: Int): Fragment {
        return SubCategoryFragment().apply {
            val bundle = Bundle(1)
            bundle.putString(SUB_CATEGORY_KEY, subCategoryList[position].subcategory_id)
            this.arguments = bundle
        }
    }

}