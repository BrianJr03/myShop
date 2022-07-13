package jr.brian.myShop.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import jr.brian.myShop.databinding.ActivitySubCategoryBinding
import jr.brian.myShop.model.remote.Category
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY
import jr.brian.myShop.model.remote.Sub
import jr.brian.myShop.model.remote.SubCategory
import jr.brian.myShop.model.remote.VolleyHelper
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryMVP
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryPresenter
import jr.brian.myShop.view.adapter.ViewPagerAdapter

class SubCategoryActivity : AppCompatActivity(), SubCategoryMVP.SubCategoryView {

    private lateinit var tabs: ArrayList<String>
    private lateinit var presenter: SubCategoryPresenter
    private lateinit var binding: ActivitySubCategoryBinding
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
        tabs = ArrayList()
        presenter = SubCategoryPresenter(VolleyHelper(this), this)
        presenter.getSubCategories(category.category_id)
        initViews()
    }

    private fun initTabLayout(subList: ArrayList<SubCategory>) {
        for (sub in subList) {
            tabs.add(sub.subcategory_name)
        }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = tabs[pos]
        }.attach()
    }

    private fun initViewPager(tabCount: Int) {
        val adapter = ViewPagerAdapter(this, tabCount)
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

    override fun setResult(sub: Sub?) {
        if (sub != null) {
            if (sub.subcategories.isNotEmpty()) {
                initViewPager(sub.subcategories.size)
                initTabLayout(sub.subcategories)
            } else {
                binding.apply {
                    errorIcon.visibility = View.VISIBLE
                    errorText.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        val pb = binding.pbSubCategory
        if (isLoading) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.GONE
        }
    }
}