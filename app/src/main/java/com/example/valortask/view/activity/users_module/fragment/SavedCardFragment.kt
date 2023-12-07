package com.example.valortask.view.activity.users_module.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.valortask.R
import com.example.valortask.databinding.FragmentSavedCardsBinding
import com.example.valortask.view.activity.users_module.adapter.SavedCardListAdapter
import com.example.valortask.view.activity.users_module.viewmodel.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SavedCardFragment : Fragment() {
    private var _binding: FragmentSavedCardsBinding? = null
    val binding get() = _binding!!


    private val viewModel by activityViewModels<HomeActivityViewModel>()

    val adapter : SavedCardListAdapter by lazy {
        SavedCardListAdapter(){
            viewModel.updateSelectedCard(it)
            navigateUp()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_cards , container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCardList.adapter = adapter


        viewModel.cardList.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty().not()){
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun navigateUp() {
        val navController = Navigation.findNavController(requireView())
        navController.navigateUp()
    }

}