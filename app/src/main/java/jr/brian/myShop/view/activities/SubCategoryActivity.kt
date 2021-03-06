package jr.brian.myShop.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import jr.brian.myShop.databinding.ActivitySubCategoryBinding
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY_KEY
import jr.brian.myShop.model.remote.category.Category
import jr.brian.myShop.model.remote.category.Sub
import jr.brian.myShop.model.remote.category.SubCategory
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryMVP
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryPresenter
import jr.brian.myShop.view.adapter.SubViewPagerAdapter

class SubCategoryActivity : AppCompatActivity(), SubCategoryMVP.SubCategoryView {

    private lateinit var subCategories: ArrayList<SubCategory>
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
        category = intent.extras?.getParcelable(SUB_CATEGORY_KEY)!!
        presenter = SubCategoryPresenter(VolleyHelper(this), this)
        presenter.getSubCategories(category.category_id)
        initViews()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, pos ->
            tab.text = subCategories[pos].subcategory_name
        }.attach()
    }

    private fun initViewPager() {
        val adapter = SubViewPagerAdapter(this, subCategories)
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

    override fun setResult(sub: Any?) {
        val s = sub as Sub
        subCategories = s.subcategories
        if (s.subcategories.isNotEmpty()) {
            initViewPager()
            initTabLayout()
        } else {
            binding.apply {
                errorIcon.visibility = View.VISIBLE
                errorText.visibility = View.VISIBLE
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        val animationView = binding.animationView
        if (!isLoading) {
            animationView.visibility = View.GONE
        }
    }
}