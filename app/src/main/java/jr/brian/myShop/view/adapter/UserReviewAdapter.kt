package jr.brian.myShop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.databinding.ProductReviewBinding
import jr.brian.myShop.model.remote.product.Review

class UserReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<UserReviewAdapter.UserReviewViewHolder>() {

    private lateinit var binding: ProductReviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ProductReviewBinding.inflate(layoutInflater, parent, false)
        return UserReviewViewHolder(binding.root)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: UserReviewViewHolder, index: Int) {
        holder.apply {
            val userReview = reviews[index]
            bind(userReview)
        }
    }

    inner class UserReviewViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(review: Review) {
            binding.apply {
                userName.text = review.full_name
                productReviewTitle.text = review.review_title
                productReviewBody.text = review.review
            }
        }
    }
}