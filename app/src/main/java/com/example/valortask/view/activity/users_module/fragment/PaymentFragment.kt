package com.example.valortask.view.activity.users_module.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.valortask.R
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.databinding.FragmentPaymentBinding
import com.example.valortask.utilities.SharedPref
import com.example.valortask.view.activity.users_module.adapter.CartListAdapter
import com.example.valortask.view.activity.users_module.adapter.PaymentSummaryAdapter
import com.example.valortask.view.activity.users_module.model.ProductList
import com.example.valortask.view.activity.users_module.viewmodel.HomeActivityViewModel
import com.example.valortask.view.activity.users_module.viewmodel.ProductListFragmentViewModel
import com.example.valortask.view.activity.users_module.viewstate.PaymentType
import com.example.valortask.view.activity.users_module.viewstate.PaymentViewState
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewState
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewType
import com.example.valortask.view.activity.users_module.fragment.PaymentFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    val binding get() = _binding!!


    @set:Inject
    var sharedPref: SharedPref? = null


    private val viewModel by activityViewModels<HomeActivityViewModel>()

    val adapter: PaymentSummaryAdapter by lazy {
        PaymentSummaryAdapter()
    }

    val df = DecimalFormat("#.##")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvPaymentList.itemAnimator = null
        binding.rvPaymentList.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    when (it) {
                        is  BaseViewState.ShowLoading ->{

                        }

                        is  BaseViewState.DismissLoading ->{

                        }

                        is BaseViewState.ShowError ->{

                            Toast.makeText(requireContext(),it.msg, Toast.LENGTH_SHORT).show()
                            viewModel.resetViewState()

                        }

                        is  PaymentViewState.PaymentStatus ->{
                            Toast.makeText(requireContext(),it.msg, Toast.LENGTH_SHORT).show()
                            viewModel.resetViewState()
                            navigateUp()

                        }

                        else->{

                        }
                    }

                }


            }
        }


        livedataObserver()


        binding.togglePaymentSelection.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            // Respond to button selection

            when(toggleButton.checkedButtonId){
                R.id.btnCard ->{
                   viewModel.paymentType.value = PaymentType.CARD
                }
                R.id.btnCash ->{
                    viewModel.paymentType.value = PaymentType.CASH
                }
            }

        }


        binding.btnSelectCard.setOnClickListener {
            val directions = PaymentFragmentDirections.actionNavigationPaymentToSavedCardFragment()
            navigateToDirection(directions)

        }


        binding.btnPlaceOrder.setOnClickListener {
            viewModel.placeOrder()
        }
    }

    fun livedataObserver() {

        viewModel.cartProductListData.observe(viewLifecycleOwner) {
            _productList ->
            adapter.submitList(_productList)

            if (_productList.isNullOrEmpty().not()) {


                val netPrice = _productList.sumOf { (it.productPrice ?: 0.0) * (it.productQuantity ?: 1) }
                val customFee = sharedPref?.customFee?.toDouble() ?: 0.0

                val percentageValue =
                    if (sharedPref?.stateTax == "0") 0.0 else (((sharedPref?.stateTax?.toDouble()
                        ?: 1.0) / 100) * netPrice.toDouble())
                val totalPrice = (netPrice + percentageValue + customFee)



                binding.netPrice = df.format(netPrice).toString()
                binding.customFee = df.format(customFee).toString()
                binding.totalPriceValue = df.format(totalPrice).toString()
                binding.stateTax = df.format(percentageValue).toString()
                viewModel.totalAmount.postValue(totalPrice)
            } else {
                binding.netPrice = "0.00"
                binding.customFee = "0.00"
                binding.totalPriceValue = "0.00"
                binding.stateTax = "0.00"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToDirection(directions: NavDirections) {
        val navController = Navigation.findNavController(requireView())
        navController.navigate(directions)
    }

    fun navigateUp() {
        val navController = Navigation.findNavController(requireView())
        navController.navigateUp()
    }


}