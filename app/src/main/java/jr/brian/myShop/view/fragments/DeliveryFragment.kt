package jr.brian.myShop.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jr.brian.myShop.databinding.FragmentDeliveryBinding
import jr.brian.myShop.view.dialog.QuickDialog

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding

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
        binding.apply {
            addAddrBtn.setOnClickListener {
                QuickDialog().show(
                    childFragmentManager, QuickDialog.DIALOG_TAG
                )
            }

        }
    }
}