package jr.brian.myShop.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.myShop.databinding.ActivityDetailsBinding
import jr.brian.myShop.model.remote.Constant.PRODUCT_ITEM_KEY
import jr.brian.myShop.model.remote.product.Detail
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.model.remote.product.Review
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.product_details_presenter.DetailsMVP
import jr.brian.myShop.presenter.product_details_presenter.DetailsPresenter
import jr.brian.myShop.view.adapter.UserReviewAdapter

class ProductDetailActivity : AppCompatActivity(), DetailsMVP.DetailsView {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var userReviewAdapter: UserReviewAdapter
    private lateinit var userReviews: ArrayList<Review>
    private lateinit var presenter: DetailsPresenter
    private lateinit var productItem: ProductItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        productItem = intent.getParcelableExtra(PRODUCT_ITEM_KEY)!!
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        binding.back.setOnClickListener { super.onBackPressed() }
    }


    private fun init() {
        userReviews = ArrayList()
        presenter = DetailsPresenter(VolleyHelper(this), this)
        presenter.getProductDetails(productItem.product_id)
        setAdapter(userReviews)
    }

    private fun setAdapter(list: ArrayList<Review>) {
        userReviewAdapter = UserReviewAdapter(list)
        binding.recyclerViewProductItem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userReviewAdapter
        }
    }


    override fun setResult(detail: Detail?) {
        if (detail != null) {
            if (detail.product.reviews.isNotEmpty()) {
                setAdapter(detail.product.reviews)
            } else {
                binding.reviewErrorGroup.visibility = View.VISIBLE
                setAdapter(ArrayList())
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
//        val animationView = binding.animationView
//        if (!isLoading) {
//            animationView.visibility = View.GONE
//        }
    }
}