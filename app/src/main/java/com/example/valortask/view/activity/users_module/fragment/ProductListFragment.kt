package com.example.valortask.view.activity.users_module.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.valortask.R
import com.example.valortask.databinding.FragmentProductListBinding
import com.example.valortask.view.activity.users_module.adapter.ProductListAdapter
import com.example.valortask.view.activity.users_module.model.convertToProductDbModel
import com.example.valortask.view.activity.users_module.viewmodel.ProductListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

   // private val viewModel by activityViewModels<ScorecardViewModel>()
    private val viewModel by viewModels<ProductListFragmentViewModel>()


    private var _binding: FragmentProductListBinding? = null
    val binding get() = _binding!!

    val adapter : ProductListAdapter by lazy {
        ProductListAdapter(){

            viewModel.insertCart(it.convertToProductDbModel())
            Toast.makeText(requireContext(),"Product added to cart",Toast.LENGTH_SHORT).show()

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list , container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //https://run.mocky.io/v3/821a00e7-c125-4b0c-977a-6077f5fc932a

        binding.rvProductList.adapter = adapter


        viewModel.productListData.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty().not()){
                adapter.submitList(it)
            }
        }

        viewModel.fetchProductList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}