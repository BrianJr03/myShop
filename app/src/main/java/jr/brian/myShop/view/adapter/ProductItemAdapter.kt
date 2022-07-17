package jr.brian.myShop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.databinding.ProductItemBinding
import jr.brian.myShop.model.remote.ProductItem

class ProductItemAdapter(
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
                startSubCategoryActivity(productItem)
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

    private fun startSubCategoryActivity(productItem: ProductItem) {
//        val intent =
//            Intent(context, SubCategoryActivity::class.java)
//        intent.putExtra(PRODUCT_ITEM_KEY, productItem)
//        context.startActivity(intent)
    }

    inner class ProductItemViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
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