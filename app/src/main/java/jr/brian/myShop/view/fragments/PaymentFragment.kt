package jr.brian.myShop.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jr.brian.myShop.databinding.FragmentPaymentBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.Constant.PAYMENT_METHOD
import jr.brian.myShop.view.activities.CheckOutActivity

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        sharedPrefHelper = SharedPrefHelper(requireContext())
        initNextBtn()
        return binding.root
    }

    private fun initNextBtn() {
        val options = arrayListOf("Cash On Delivery", "Cryptocurrency", "Debit / Credit Card")
        binding.apply {
            saveBtn.setOnClickListener {
                val index =
                    radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.checkedRadioButtonId))
                val selected = options[index]
                sharedPrefHelper.editor.putString(PAYMENT_METHOD, selected).commit()
                (activity as CheckOutActivity).slideViewPager()
            }
        }
    }
}