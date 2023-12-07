package com.example.valortask.view.activity.users_module.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.valortask.R
import com.example.valortask.databinding.FragmentCartBinding
import com.example.valortask.view.activity.users_module.adapter.CartListAdapter
import com.example.valortask.view.activity.users_module.model.convertToProductDbModel
import com.example.valortask.view.activity.users_module.viewmodel.CartFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    val binding get() = _binding!!


    private val viewModel by viewModels<CartFragmentViewModel>()

    val adapter : CartListAdapter by lazy {
        CartListAdapter(){ type, model ->

            when(type){
                "Delete"->{
                    viewModel.deleteCartProduct(model.convertToProductDbModel())
                }
                "Inc" ->{
                   val qty = model.productQuantity?.plus(1)

                    qty?.let{_qty ->
                        model.productId?.let {_id ->
                            viewModel.updateProduct(_qty,_id)
                        }
                    }
                }
                else ->{
                    val qty = model.productQuantity?.minus(1)
                    if(qty != 0){
                        qty?.let{_qty ->
                            model.productId?.let {_id ->
                                viewModel.updateProduct(_qty,_id)
                            }
                        }

                    }

                }
            }


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart , container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCartProductList.itemAnimator = null
        binding.rvCartProductList.adapter = adapter


        viewModel.productListData.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty().not()){
                adapter.submitList(it)
            }else{
                adapter.submitList(emptyList())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}