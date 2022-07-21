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
import jr.brian.myShop.model.remote.Constant.USER_ID
import jr.brian.myShop.model.remote.order.AddOrderInput
import jr.brian.myShop.model.remote.order.DeliveryAddress
import jr.brian.myShop.model.remote.order.Item
import jr.brian.myShop.model.remote.product.ProductItem
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.order_presenter.OrderMVP
import jr.brian.myShop.presenter.order_presenter.OrderPresenter
import jr.brian.myShop.view.activities.OrderConfirmedActivity
import jr.brian.myShop.view.adapter.CheckedOutItemsAdapter

class SummaryFragment : Fragment(), OrderMVP.OrderView {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var adapter: CheckedOutItemsAdapter
    private lateinit var presenter: OrderPresenter
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var addOrderInput: AddOrderInput

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
        presenter = OrderPresenter(VolleyHelper(requireContext()), this)
        binding.apply {
            placeOrderBtn.setOnClickListener {
                presenter.placeOrder(addOrderInput)
            }
        }
    }

    private fun initView() {
        val userId = sharedPrefHelper.encryptedSharedPrefs.getString(USER_ID, "0").toString()
        val address = sharedPrefHelper.encryptedSharedPrefs.getString(DELIVERY_ADDRESS, null)
        val addressSplit = address?.split(" | ")
        val deliveryAddressObj = DeliveryAddress(
            addressSplit?.get(0) ?: "Office",
            addressSplit?.get(1) ?: "Microsoft HQ"
        )
        val payment = sharedPrefHelper.encryptedSharedPrefs.getString(PAYMENT_METHOD, null)
        val json = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)

        val items = arrayListOf<Item>()
        if (json != null) {
            var cartTotal = 0
            val cart = getCart().distinct()
            for (p in cart) {
                cartTotal += p.total
                val id = p.product_id
                val qty = p.qty
                val price = p.price
                items.add(Item(id.toInt(), qty, price.toInt()))
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
            addOrderInput = AddOrderInput(
                cartTotal,
                deliveryAddressObj,
                items,
                payment.toString(),
                userId.toInt()
            )
        }
    }

    private fun getCart(): ArrayList<ProductItem> {
        val gson = Gson()
        val json: String? = sharedPrefHelper.encryptedSharedPrefs.getString(CART, null)
        val type = object : TypeToken<ArrayList<ProductItem>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun setResult(message: Any?) {
        startActivity(
            Intent
                (requireContext(), OrderConfirmedActivity::class.java)
        )
        activity?.finish()
    }

    override fun onLoad(isLoading: Boolean) {
    }
}