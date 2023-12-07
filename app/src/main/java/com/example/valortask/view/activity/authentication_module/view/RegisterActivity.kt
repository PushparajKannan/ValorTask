package com.example.valortask.view.activity.authentication_module.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.valortask.R
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.databinding.ActivityRegisterBinding
import com.example.valortask.utilities.checkValidEmail
import com.example.valortask.utilities.checkValidPasswordLogin
import com.example.valortask.view.activity.authentication_module.viewmodel.RegisterViewModel
import com.example.valortask.view.activity.authentication_module.viewstate.RegisterViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    when (it) {


                       is BaseViewState.ShowError ->{

                           Toast.makeText(this@RegisterActivity,it.msg,Toast.LENGTH_SHORT).show()
                           viewModel.resetViewState()

                        }

                      is  RegisterViewState.UserRegisterSuccessFully ->{
                          Toast.makeText(this@RegisterActivity,it.msg,Toast.LENGTH_SHORT).show()
                          viewModel.resetViewState()
                          finish()

                        }

                    }

                }


            }
        }


        initViews()
        liveDataObservers()
        onClickListeners()
    }

    private fun initViews(){

    }

    private fun liveDataObservers(){


        viewModel.userMail.observe(this){
            binding.tvEmail.checkValidEmail(it)
        }

        viewModel.userPassword.observe(this){
            binding.tvPassword.checkValidPasswordLogin(it)
        }

        viewModel.userConformPassword.observe(this){
            binding.tvConformPassword.checkValidPasswordLogin(it)
        }

    }

    private fun onClickListeners(){
        binding.btnRegister.setOnClickListener {
            viewModel.registerUser()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    /*fun initViews(){

    }

    fun liveDataObservers(){

    }

    fun onClickListeners(){

    }*/
}