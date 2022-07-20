package jr.brian.myShop.view.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import jr.brian.myShop.R
import jr.brian.myShop.databinding.FragmentDeliveryBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.remote.address.Address
import jr.brian.myShop.model.remote.address.GetAddressesResponse
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.address_presenter.AddressMVP
import jr.brian.myShop.presenter.address_presenter.AddressPresenter
import jr.brian.myShop.view.auth_fragments.SignInFragment
import jr.brian.myShop.view.dialog.QuickDialog

class DeliveryFragment : Fragment(), AddressMVP.AddressView {
    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var presenter: AddressPresenter
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var userId: String
    private lateinit var addresses: List<Address>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        sharedPrefHelper = SharedPrefHelper(requireContext()).apply {
            userId = encryptedSharedPrefs.getString(SignInFragment.USER_ID, "0").toString()
        }
        presenter = AddressPresenter(VolleyHelper(requireContext()), this)
        presenter.getAddresses(userId)
        binding.apply {
            addAddrBtn.setOnClickListener {
                QuickDialog().show(
                    childFragmentManager, QuickDialog.DIALOG_TAG
                )
            }
        }
    }

    override fun setResult(message: Any?, type: String) {
        val addr = message as GetAddressesResponse
        addresses = addr.addresses
        for (a in addresses) {
            val rb = RadioButton(requireContext())
            rb.apply {
                layoutParams = ConstraintLayout
                    .LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                text = "${a.title} | ${a.address}"
                setTextColor(getColor(requireContext(), R.color.blueish_idk))
                textSize = 15f
                typeface = Typeface.DEFAULT_BOLD
                layoutDirection = ViewCompat.LAYOUT_DIRECTION_LTR
            }
            binding.radioGroup.addView(rb)
            Log.i("HELPHELP", a.toString())
        }
    }

    override fun onLoad(isLoading: Boolean) {
        // TODO
    }
}