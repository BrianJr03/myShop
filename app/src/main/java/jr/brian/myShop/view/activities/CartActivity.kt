package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.myShop.databinding.ActivityCartBinding
import jr.brian.myShop.databinding.CartItemBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartItemBinding: CartItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
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

        initQtyBTNS()
    }

    private fun initQtyBTNS() {
        binding.apply {
            var qty = 1
            incQtyBtn.setOnClickListener {
//                var qty = 1
//                var qty = productItem.qty
//                val price = productItem.price.toInt()
                qty++
                qtyTv.text = qty.toString()
                val price = (qty * productPrice.text.toString().toInt()).toString()
                productPriceTotal.text = price
                productPriceTotalCard.text = price
//                productItem.qty = qty
//                productItem.price = (qty * price).toString()
            }
            decQtyBtn.setOnClickListener {
//                var qty = 1
//                var qty = productItem.qty
//                val price = productItem.price.toInt()
                qty--
                if (qty < 1) {
                    qty = 1
//                    productItem.qty = 1
//                    productItem.price = price.toString()
//                    productAddToCart.visibility = View.VISIBLE
//                    qtyGroup.visibility = View.GONE
                }
                qtyTv.text = qty.toString()
                val price = (qty * productPrice.text.toString().toInt()).toString()
                productPriceTotal.text = price
                productPriceTotalCard.text = price
//                productItem.qty = qty
//                productItem.price = (qty * price).toString()
            }
        }
    }
}