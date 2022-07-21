package jr.brian.myShop.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.databinding.FragmentCartItemsBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.view.adapter.CheckedOutItemsAdapter

class CartItemsFragment : Fragment() {
    private lateinit var binding: FragmentCartItemsBinding
    private lateinit var adapter: CheckedOutItemsAdapter
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPrefHelper = SharedPrefHelper(requireContext())
        binding = FragmentCartItemsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString("cart", null)
        if (json != null) {
            var cartTotal = 0
            val cart = getCart().distinct()
            for (p in cart) {
                cartTotal += p.total
            }
            binding.cartTotalCheckOut.text = cartTotal.toString()
            adapter = CheckedOutItemsAdapter(cart)
            binding.recyclerViewProductItem.layoutManager =
                LinearLayoutManager(requireContext())
            binding.recyclerViewProductItem.adapter = adapter
        }
    }

    private fun getCart(): ArrayList<ProductItem> {
        val gson = Gson()
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString("cart", null)
        val type = object : TypeToken<ArrayList<ProductItem>>() {}.type
        return gson.fromJson(json, type)
    }
}