package jr.brian.myShop.view.sub_category_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myShop.R
import jr.brian.myShop.databinding.FragmentSubCategoryOneBinding
import jr.brian.myShop.databinding.ProductItemBinding
import jr.brian.myShop.model.remote.ProductItem
import jr.brian.myShop.view.adapter.ProductItemAdapter

class SubCategoryOneFragment : Fragment() {

    private lateinit var productItems: ArrayList<ProductItem>
    private lateinit var productItemAdapter: ProductItemAdapter
    private lateinit var binding: FragmentSubCategoryOneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentSubCategoryOneBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.product_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        productItems = ArrayList<ProductItem>().apply {
            for (i in 1..5) {
                add(
                    ProductItem(
                        "2",
                        "1",
                        "Smart Phones",
                        getString(R.string.example_product_descr),
                        "$200",
                        "1",
                        "",
                        "Samsung S8",
                        "1",
                        "Android Phones"
                    )
                )
            }
        }
        setAdapter(view, productItems)
    }

    private fun setAdapter(view: View, list: ArrayList<ProductItem>) {
        productItemAdapter = ProductItemAdapter(view.context, list)
        setLinearLayout(view)
    }

    private fun setLinearLayout(view: View) {
        binding.recyclerViewProductItem.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = productItemAdapter
        }

//        val rv = view.findViewById<RecyclerView>(R.id.recycler_view_product_item)
//        rv.layoutManager =
//            LinearLayoutManager(view.context)
//        rv.adapter = productItemAdapter
    }
}