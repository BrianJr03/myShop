package jr.brian.myShop.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jr.brian.myShop.databinding.CategoryBinding
import jr.brian.myShop.model.remote.Constant.BASE_IMAGE_URL
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY_KEY
import jr.brian.myShop.model.remote.category.Category
import jr.brian.myShop.view.activities.SubCategoryActivity

class CategoryAdapter(private val context: Context, private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var binding: CategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = CategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding.root)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, index: Int) {
        holder.apply {
            val category = categories[index]
            bind(category)
            itemView.setOnClickListener {
                startSubCategoryActivity(category)
            }
        }
    }

    private fun startSubCategoryActivity(category: Category) {
        val intent =
            Intent(context, SubCategoryActivity::class.java)
        intent.putExtra(SUB_CATEGORY_KEY, category)
        context.startActivity(intent)
    }

    inner class CategoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(category: Category) {
            binding.apply {
                Glide.with(context)
                    .load(BASE_IMAGE_URL + category.category_image_url)
                    .into(categoryImage)
                categoryName.text = category.category_name
            }
        }
    }
}