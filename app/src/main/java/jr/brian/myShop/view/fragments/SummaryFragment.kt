package jr.brian.myShop.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jr.brian.myShop.databinding.FragmentSummaryBinding
import jr.brian.myShop.view.activities.OrderConfirmedActivity

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
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
}