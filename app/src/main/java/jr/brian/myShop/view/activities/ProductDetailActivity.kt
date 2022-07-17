package jr.brian.myShop.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.myShop.R
import jr.brian.myShop.databinding.ActivityProductDetailBinding
import jr.brian.myShop.model.local.replaceFragment
import jr.brian.myShop.view.fragments.ProductDetailFragment
import jr.brian.myShop.view.fragments.ProductReviewFragment
import jr.brian.myShop.view.fragments.ProductSpecsFragment

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        replaceFragment(R.id.container_details, ProductDetailFragment())
        replaceFragment(R.id.container_specs, ProductSpecsFragment())
        replaceFragment(R.id.container_reviews, ProductReviewFragment())
    }
}