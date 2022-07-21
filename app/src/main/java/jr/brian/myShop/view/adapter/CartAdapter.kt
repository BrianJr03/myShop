package jr.brian.myShop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jr.brian.myShop.R
import jr.brian.myShop.databinding.CartItemBinding
import jr.brian.myShop.model.remote.Constant
import jr.brian.myShop.model.remote.product.ProductItem

class CartAdapter(private val cart: List<ProductItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private lateinit var binding: CartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = CartItemBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding.root)
    }

    override fun getItemCount() = cart.size

    override fun onBindViewHolder(holder: CartViewHolder, index: Int) {
        holder.apply {
            val cartItem = cart[index]
            bind(cartItem)
        }
    }

    inner class CartViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(productItem: ProductItem) {
            binding.apply {
                Glide.with(v.context)
                    .load(Constant.BASE_IMAGE_URL + productItem.product_image_url)
                    .placeholder(R.drawable.phone_90_70)
                    .error(R.drawable.phone_90_70)
                    .fallback(R.drawable.phone_90_70)
                    .into(productImage)
                productName.text = productItem.product_name
                productDescr.text = productItem.description
                qtyTv.text = productItem.qty.toString()
                total.text = productItem.price
                productPriceTotal.text = (productItem.price.toInt() * productItem.qty).toString()

            }
        }
    }
}