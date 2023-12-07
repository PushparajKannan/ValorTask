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
import com.example.valortask.R
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.databinding.FragmentProfileBinding
import com.example.valortask.utilities.checkValidEmail
import com.example.valortask.view.activity.users_module.viewmodel.HomeActivityViewModel
import com.example.valortask.view.activity.users_module.viewmodel.ProfileFragmentViewModel
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewState
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private val viewModel by viewModels<ProfileFragmentViewModel>()

    private val activityViewModel by activityViewModels<HomeActivityViewModel>()


    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile , container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateProfileInfo(activityViewModel.loggedInUserInfo.value)


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

                        is  ProfileViewState.ProfileUpdated ->{
                            Toast.makeText(requireContext(),it.msg, Toast.LENGTH_SHORT).show()
                            viewModel.currentViewType.postValue(ProfileViewType.VIEW)
                            viewModel.resetViewState()

                        }

                        else->{

                        }
                    }

                }


            }
        }



        initViews()
        liveDataObservers()
        onClickListeners()

    }

    fun initViews(){

    }

    fun liveDataObservers(){
        viewModel.userMail.observe(viewLifecycleOwner){
            binding.editEmail.checkValidEmail(it)
        }
    }

    fun onClickListeners(){
        binding.btnEditProfile.setOnClickListener {

            if(viewModel.currentViewType.value == ProfileViewType.VIEW){
                viewModel.currentViewType.postValue(ProfileViewType.EDIT)
            }else{

                activityViewModel.loggedInUserInfo.value?.let {
                    viewModel.updateProfile(it)

                }



            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}