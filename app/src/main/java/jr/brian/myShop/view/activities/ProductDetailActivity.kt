package jr.brian.myShop.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
        initAddToCartBtn()
    }

    private fun setAdapter(list: ArrayList<Review>) {
        userReviewAdapter = UserReviewAdapter(list)
        binding.recyclerViewProductItem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userReviewAdapter
        }
    }

    private fun initMainCard(detail: Detail) {
        binding.apply {
//            Glide.with(this@ProductDetailActivity)
//                .load(BASE_IMAGE_URL + productItem.product_image_url)
//                .into(productImage)
            productDescr.text = detail.product.description
            productName.text = detail.product.product_name
            total.text = detail.product.price
            productRating.rating = productItem.average_rating.toFloat()
        }
    }

    private fun initSpecsCard(detail: Detail) {
        binding.apply {
            modelNameSpecs.text = detail.product.product_name
        }
    }

    private fun initAddToCartBtn() {
        binding.apply {
            productAddToCart.setOnClickListener {
                productAddToCart.visibility = View.GONE
                qtyGroup.visibility = View.VISIBLE
                if (qtyGroup.isVisible) {
                    incQtyBtn.setOnClickListener {
                        var qty = productItem.qty
                        val price = productItem.price.toInt()
                        qty++
                        qtyTv.text = qty.toString()
                        productItem.qty = qty
                        productItem.price = (qty * price).toString()
                    }
                    decQtyBtn.setOnClickListener {
                        var qty = productItem.qty
                        val price = productItem.price.toInt()
                        qty--
                        if (qty < 1) {
                            qty = 1
                            productItem.qty = 1
                            productItem.price = price.toString()
                            productAddToCart.visibility = View.VISIBLE
                            qtyGroup.visibility = View.GONE
                        }
                        qtyTv.text = qty.toString()
                        productItem.qty = qty
                        productItem.price = (qty * price).toString()
                    }
                }
            }
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
            initMainCard(detail)
            initSpecsCard(detail)
        }
    }

    override fun onLoad(isLoading: Boolean) {
        val animationView = binding.animationView
        binding.apply {
            cardViewMain.visibility = View.GONE
            cardViewDetail.visibility = View.GONE
            detailsTv.visibility = View.GONE
        }
        if (!isLoading) {
            animationView.visibility = View.GONE
            binding.apply {
                cardViewMain.visibility = View.VISIBLE
                cardViewDetail.visibility = View.VISIBLE
                detailsTv.visibility = View.VISIBLE
            }
        }
    }
}