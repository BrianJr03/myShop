package jr.brian.myShop.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.R

class ViewPagerAdapter(private val context: Context, private val images: ArrayList<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vp_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.apply {
            bind()
        }
    }

    inner class ViewPagerViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind() {
            val textView = v.findViewById<TextView>(R.id.textItem_vp)
            textView.text = images[layoutPosition]
        }
    }
}