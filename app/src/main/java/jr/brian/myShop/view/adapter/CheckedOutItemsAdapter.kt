package jr.brian.myShop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.databinding.CheckoutCartItemBinding
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

    inner class CheckedOutItemsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(productItem: ProductItem) {
            binding.apply {
//                Glide.with(context)
//                    .load(BASE_IMAGE_URL + productItem.product_image_url)
//                    .into(productImage)
                productName.text = productItem.product_name
                price.text = productItem.price
                total.text = productItem.total.toString()
            }
        }
    }
}