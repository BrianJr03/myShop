package jr.brian.myShop.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import jr.brian.myShop.R
import jr.brian.myShop.databinding.AddAddressDialogBinding
import jr.brian.myShop.model.local.SharedPrefHelper
import jr.brian.myShop.model.local.showSnackbar
import jr.brian.myShop.model.remote.volley.VolleyHelper
import jr.brian.myShop.presenter.address_presenter.AddressMVP
import jr.brian.myShop.presenter.address_presenter.AddressPresenter
import jr.brian.myShop.view.auth_fragments.SignInFragment

class QuickDialog : DialogFragment(), AddressMVP.AddressView {

    private lateinit var binding: AddAddressDialogBinding
    private lateinit var presenter: AddressPresenter
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddAddressDialogBinding.inflate(layoutInflater, container, false)
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
        binding.apply {
            backBtn.setOnClickListener {
                dismiss()
            }
            saveBtn.setOnClickListener {
                presenter.addAddress(userId, addrTitleEt.text.toString(), addrEt.text.toString())
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
    }

    companion object {
        const val DIALOG_TAG = "dialog_tag"
    }

    override fun setResult(message: Any?, type: String) {
        view?.let { showSnackbar(message.toString(), it, R.id.delivery_root) }
    }

    override fun onLoad(isLoading: Boolean) {
        // TODO
    }
}