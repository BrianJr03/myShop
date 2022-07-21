package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.databinding.ActivityCartBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Constant.CART
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.view.adapter.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        sharedPrefHelper = SharedPrefHelper(this)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
            back.setOnClickListener { super.onBackPressed() }
            checkOutBtn.setOnClickListener {
                startActivity(
                    Intent(
                        this@CartActivity,
                        CheckOutActivity::class.java
                    )
                )
            }
        }
        initView()
    }

    private fun initView() {
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)
        if (json != null) {
            var cartTotal = 0
            val cart = getCart().distinct()
            for (p in cart) {
                cartTotal += p.total
            }
            binding.cartTotal.text = cartTotal.toString()
            adapter = CartAdapter(cart)
            binding.recyclerViewProductItem.layoutManager =
                LinearLayoutManager(this)
            binding.recyclerViewProductItem.adapter = adapter
        }
    }

    private fun getCart(): ArrayList<ProductItem> {
        val gson = Gson()
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)
        val type = object : TypeToken<ArrayList<ProductItem>>() {}.type
        return gson.fromJson(json, type)
    }
}