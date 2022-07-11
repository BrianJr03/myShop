package jr.brian.myShop.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.databinding.CategoryBinding
import jr.brian.myShop.model.remote.Category

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
            bind()
            itemView.setOnClickListener {

            }
        }
    }

    inner class CategoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind() {
            binding.apply {

            }
        }
    }

    companion object {
        const val CATEGORY_DATA = "note"
    }
}