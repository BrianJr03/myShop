package jr.brian.myShop.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import jr.brian.myShop.databinding.ActivitySubCategoryBinding
import jr.brian.myShop.view.adapter.ViewPagerAdapter

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = (pos + 1).toString()
        }.attach()
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(this, 3)
        binding.pager.adapter = adapter
    }

    override fun onBackPressed() {
        val vp = binding.pager
        if (vp.currentItem == 0) {
            super.onBackPressed()
        } else vp.currentItem = vp.currentItem - 1
    }
}