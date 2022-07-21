package jr.brian.myShop.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jr.brian.myShop.databinding.FragmentSummaryBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Constant.CART
import jr.brian.myShop.model.remote.Constant.DELIVERY_ADDRESS
import jr.brian.myShop.model.remote.Constant.PAYMENT_METHOD
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.view.activities.OrderConfirmedActivity
import jr.brian.myShop.view.adapter.CheckedOutItemsAdapter

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var adapter: CheckedOutItemsAdapter
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        sharedPrefHelper = SharedPrefHelper(requireContext())
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.apply {
            placeOrderBtn.setOnClickListener {
                startActivity(
                    Intent(requireContext(), OrderConfirmedActivity::class.java)
                )
                activity?.finish()
            }
        }
    }

    private fun initView() {
        val address = sharedPrefHelper.encryptedSharedPrefs.getString(DELIVERY_ADDRESS, null)
        val payment = sharedPrefHelper.encryptedSharedPrefs.getString(PAYMENT_METHOD, null)
        val json = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)
        if (json != null) {
            var cartTotal = 0
            val cart = getCart().distinct()
            for (p in cart) {
                cartTotal += p.total
            }
            binding.apply {
                cartTotalCheckOut.text = cartTotal.toString()
                deliveryAddr.text = address
                paymentOption.text = payment
                adapter = CheckedOutItemsAdapter(cart)
                recyclerViewProductItem.layoutManager =
                    LinearLayoutManager(requireContext())
                recyclerViewProductItem.adapter = adapter
            }
        }

    }

    private fun getCart(): ArrayList<ProductItem> {
        val gson = Gson()
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)
        val type = object : TypeToken<ArrayList<ProductItem>>() {}.type
        return gson.fromJson(json, type)
    }
}