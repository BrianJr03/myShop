package jr.brian.myShop.view.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.myShop.databinding.ActivitySubCategoryBinding
import jr.brian.myShop.view.adapter.ViewPagerAdapter

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var strings: ArrayList<String>
    private lateinit var binding: ActivitySubCategoryBinding
    private lateinit var viewPageAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
    }

    private fun initData() {
        strings = ArrayList<String>().apply {
            add("Android")
            add(("iPhone"))
            add("Windows")
        }
    }

    private fun initView() {
        viewPageAdapter = ViewPagerAdapter(this, strings)
        binding.pager.adapter = viewPageAdapter
    }
}