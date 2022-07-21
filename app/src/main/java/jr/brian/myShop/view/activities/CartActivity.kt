package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.databinding.ActivityCartBinding
import jr.brian.myShop.model.local.SharedPrefHelper
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
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString("cart", null)
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
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString("cart", null)
        val type = object : TypeToken<ArrayList<ProductItem>>() {}.type
        return gson.fromJson(json, type)
    }

//    private fun initQtyBTNS() {
//        binding.apply {
//            var qty = 1
//            incQtyBtn.setOnClickListener {
////                var qty = 1
////                var qty = productItem.qty
////                val price = productItem.price.toInt()
//                qty++
//                qtyTv.text = qty.toString()
//                val price = (qty * productPrice.text.toString().toInt()).toString()
//                productPriceTotal.text = price
//                productPriceTotalCard.text = price
////                productItem.qty = qty
////                productItem.price = (qty * price).toString()
//            }
//            decQtyBtn.setOnClickListener {
////                var qty = 1
////                var qty = productItem.qty
////                val price = productItem.price.toInt()
//                qty--
//                if (qty < 1) {
//                    qty = 1
////                    productItem.qty = 1
////                    productItem.price = price.toString()
////                    productAddToCart.visibility = View.VISIBLE
////                    qtyGroup.visibility = View.GONE
//                }
//                qtyTv.text = qty.toString()
//                val price = (qty * productPrice.text.toString().toInt()).toString()
//                productPriceTotal.text = price
//                productPriceTotalCard.text = price
////                productItem.qty = qty
////                productItem.price = (qty * price).toString()
//            }
//        }
//    }
}