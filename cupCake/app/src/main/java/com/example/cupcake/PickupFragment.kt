package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentPickupBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {

    // Binding object instance corresponding to the fragment_pickup.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentPickupBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            pickupFragment = this@PickupFragment
        }
    }

    /**
     * Navigate to the next screen to see the order summary.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * To clear the view Model and navigate back to start screen when Cancal order btn clicked
     */
    fun cancelOrder(){
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
    }
}