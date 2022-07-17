package jr.brian.myShop.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.databinding.ProductItemBinding
import jr.brian.myShop.model.remote.Constant.PRODUCT_ITEM_KEY
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.view.activities.ProductDetailActivity

class ProductItemAdapter(
    private val context: Context,
    private val productItems: List<ProductItem>
) :
    RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder>() {

    private lateinit var binding: ProductItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ProductItemBinding.inflate(layoutInflater, parent, false)
        return ProductItemViewHolder(binding.root)
    }

    override fun getItemCount() = productItems.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, index: Int) {
        holder.apply {
            val productItem = productItems[index]
            bind(productItem)
            itemView.setOnClickListener {
                startProductDetailActivity(productItem)
            }
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
    }

    private fun startProductDetailActivity(productItem: ProductItem) {
        val intent =
            Intent(context, ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ITEM_KEY, productItem)
        context.startActivity(intent)
    }

    inner class ProductItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(productItem: ProductItem) {
            binding.apply {
//                Glide.with(v.context)
//                    .load(BASE_IMAGE_URL + productItem.product_image_url)
//                    .into(productImage)
                productName.text = productItem.product_name
                productDescr.text = productItem.description
                productPrice.text = productItem.price
                productRating.rating = productItem.average_rating.toFloat()
            }
        }
    }
}