package jr.brian.myShop.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityCheckOutBinding
import jr.brian.myShop.view.adapter.CheckOutViewPageAdapter

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
    }

    fun slideViewPager() {
        findViewById<ViewPager2>(R.id.pager).currentItem =
            findViewById<ViewPager2>(R.id.pager).currentItem + 1
    }

    private fun init() {
        initViewPager()
        initTabLayout()
        binding.apply {
            back.setOnClickListener {
                super.onBackPressed()
            }
        }
    }

    private fun initTabLayout() {
        val list = arrayListOf("Cart Items", "Delivery", "Payment", "Summary")
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = list[pos]
        }.attach()
    }

    private fun initViewPager() {
        val adapter = CheckOutViewPageAdapter(this, 4)
        binding.pager.adapter = adapter
    }

    override fun onBackPressed() {
        val vp = binding.pager
        if (vp.currentItem == 0) {
            super.onBackPressed()
        } else vp.currentItem = vp.currentItem - 1
    }
}