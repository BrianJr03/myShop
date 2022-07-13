package jr.brian.myShop.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import jr.brian.myShop.databinding.ActivitySubCategoryBinding
import jr.brian.myShop.model.remote.Category
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY
import jr.brian.myShop.model.remote.SubCategories
import jr.brian.myShop.model.remote.VolleyHelper
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryMVP
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryPresenter
import jr.brian.myShop.view.adapter.ViewPagerAdapter

class SubCategoryActivity : AppCompatActivity(), SubCategoryMVP.SubCategoryView {

    private lateinit var tabs: ArrayList<String>
    private lateinit var subCategories: ArrayList<SubCategories>
    private lateinit var presenter: SubCategoryPresenter
    private lateinit var binding: ActivitySubCategoryBinding
    private var numOfTabs: Int = 0
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
    }

    private fun init() {
        category = intent.extras?.getParcelable(SUB_CATEGORY)!!
        subCategories = ArrayList()
        presenter = SubCategoryPresenter(VolleyHelper(this), this)
        presenter.getSubCategories(category.category_id)
        initViewPager()
        initTabLayout()
        initViews()
    }

    private fun initTabLayout() {
        tabs = arrayListOf()
        for (sub in subCategories) {
            for (s in sub.subcategories) {
                tabs.add(s.subcategory_name)
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = tabs[pos]
        }.attach()
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(this, numOfTabs)
        binding.pager.adapter = adapter
    }

    private fun initViews() {
        binding.categoriesTv.text = category.category_name
        binding.back.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        val vp = binding.pager
        if (vp.currentItem == 0) {
            super.onBackPressed()
        } else vp.currentItem = vp.currentItem - 1
    }

    override fun setResult(subCategories: SubCategories?) {
        if (subCategories != null) {
            numOfTabs = subCategories.subcategories.size
        }
    }

    override fun onLoad(isLoading: Boolean) {
    }
}