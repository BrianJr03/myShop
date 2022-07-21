package jr.brian.myShop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jr.brian.myShop.R
import jr.brian.myShop.databinding.CheckoutCartItemBinding
import jr.brian.myShop.model.remote.Constant
import jr.brian.myShop.model.remote.product.ProductItem

class CheckedOutItemsAdapter(private val cart: List<ProductItem>) :
    RecyclerView.Adapter<CheckedOutItemsAdapter.CheckedOutItemsViewHolder>() {

    private lateinit var binding: CheckoutCartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckedOutItemsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = CheckoutCartItemBinding.inflate(layoutInflater, parent, false)
        return CheckedOutItemsViewHolder(binding.root)
    }

    override fun getItemCount() = cart.size

    override fun onBindViewHolder(holder: CheckedOutItemsViewHolder, index: Int) {
        holder.apply {
            val cartItem = cart[index]
            bind(cartItem)
            itemView.setOnClickListener {

            }
        }
    }

    inner class CheckedOutItemsViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(productItem: ProductItem) {
            binding.apply {
                Glide.with(v.context)
                    .load(Constant.BASE_IMAGE_URL + productItem.product_image_url)
                    .placeholder(R.drawable.phone_90_70)
                    .error(R.drawable.phone_90_70)
                    .fallback(R.drawable.phone_90_70)
                    .into(productImage)
                productName.text = productItem.product_name
                price.text = productItem.price
                total.text = productItem.total.toString()
            }
        }
    }
}