package jr.brian.myShop.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityProductDetailBinding
import jr.brian.myShop.databinding.FragmentProductReviewBinding
import jr.brian.myShop.model.local.replaceFragment
import jr.brian.myShop.model.remote.product.Detail
import jr.brian.myShop.model.remote.product.ProductDetails
import jr.brian.myShop.model.remote.product.Review
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.product_details_presenter.DetailsMVP
import jr.brian.myShop.presenter.product_details_presenter.DetailsPresenter
import jr.brian.myShop.view.adapter.UserReviewAdapter
import jr.brian.myShop.view.fragments.ProductDetailFragment
import jr.brian.myShop.view.fragments.ProductReviewFragment
import jr.brian.myShop.view.fragments.ProductSpecsFragment

class ProductDetailActivity : AppCompatActivity(), DetailsMVP.DetailsView {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var reviewBinding: FragmentProductReviewBinding
    private lateinit var presenter: DetailsPresenter
    private lateinit var userReviews: List<Review>
    private lateinit var userReviewAdapter: UserReviewAdapter
    private lateinit var productDetails: ProductDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.back.setOnClickListener { super.onBackPressed() }
        replaceFragment(R.id.container_details, ProductDetailFragment())
        replaceFragment(R.id.container_specs, ProductSpecsFragment())

    }


    private fun init() {
        presenter = DetailsPresenter(VolleyHelper(this), this)
        presenter.getProductDetails(productDetails.product_id)
    }


    override fun setResult(detail: Detail?) {
        productDetails = detail?.product!!
        userReviews = detail.product.reviews
        if (userReviews.isEmpty()) {
            binding.apply {
//                errorIcon.visibility = View.VISIBLE
//                errorText.visibility = View.VISIBLE
            }
        } else {
            userReviewAdapter = UserReviewAdapter(userReviews)
            reviewBinding.recyclerViewProductItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userReviewAdapter
            }
        }
        init()
        replaceFragment(R.id.container_reviews, ProductReviewFragment())
    }

    override fun onLoad(isLoading: Boolean) {
//        val animationView = binding.animationView
//        if (!isLoading) {
//            animationView.visibility = View.GONE
//        }
    }
}