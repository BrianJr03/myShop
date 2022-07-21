package jr.brian.myShop.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jr.brian.myShop.databinding.ActivityOrderConfirmedBinding

class OrderConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            back.setOnClickListener {
                startCategoryActivity()
            }
        }
    }

    private fun startCategoryActivity() {
        startActivity(
            Intent(this@OrderConfirmedActivity, CategoryActivity::class.java)
        )
        finish()
    }

    override fun onBackPressed() {
        startCategoryActivity()
    }
}