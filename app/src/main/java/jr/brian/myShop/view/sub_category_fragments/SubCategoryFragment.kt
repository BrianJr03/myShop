package jr.brian.myShop.view.sub_category_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jr.brian.myShop.databinding.FragmentSubCategoryBinding
import jr.brian.myShop.model.remote.Constant.SUB_CATEGORY_KEY
import jr.brian.myShop.model.remote.Product
import jr.brian.myShop.model.remote.ProductItem
import jr.brian.myShop.model.remote.VolleyHelper
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryMVP
import jr.brian.myShop.presenter.sub_category_presenter.SubCategoryPresenter
import jr.brian.myShop.view.adapter.ProductItemAdapter

class SubCategoryFragment : Fragment(), SubCategoryMVP.SubCategoryView {

    private lateinit var subCategoryId: String
    private lateinit var presenter: SubCategoryPresenter
    private lateinit var productItems: List<ProductItem>
    private lateinit var productItemAdapter: ProductItemAdapter
    private lateinit var binding: FragmentSubCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        subCategoryId = arguments?.getString(SUB_CATEGORY_KEY).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        presenter = SubCategoryPresenter(VolleyHelper(requireContext()), this)
        presenter.loadSubCategoryProducts(subCategoryId)
    }

    override fun setResult(sub: Any?) {
        productItems = (sub as Product).products
        if (productItems.isEmpty()) {
            binding.apply {
                errorIcon.visibility = View.VISIBLE
                errorText.visibility = View.VISIBLE
            }
        } else {
            productItemAdapter = ProductItemAdapter(productItems)
            binding.recyclerViewProductItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = productItemAdapter
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        val animationView = binding.animationView
        if (isLoading) {
            animationView.visibility = View.VISIBLE
        } else {
            animationView.visibility = View.GONE
        }
    }
}