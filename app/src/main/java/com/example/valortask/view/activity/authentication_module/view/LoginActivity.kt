package com.example.valortask.view.activity.authentication_module.view

import android.content.Intent
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
import com.example.valortask.databinding.ActivityLoginBinding
import com.example.valortask.view.activity.authentication_module.viewmodel.LoginViewModel
import com.example.valortask.view.activity.authentication_module.viewstate.LoginViewState
import com.example.valortask.view.activity.users_module.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()


    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    when (it) {
                        is  BaseViewState.ShowLoading ->{

                        }

                        is  BaseViewState.DismissLoading ->{

                        }

                        is BaseViewState.ShowError ->{

                            Toast.makeText(this@LoginActivity,it.msg, Toast.LENGTH_SHORT).show()
                            viewModel.resetViewState()

                        }

                        is  LoginViewState.UserLoggedInSuccessFully ->{
                            viewModel.resetViewState()
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@LoginActivity,it.msg, Toast.LENGTH_SHORT).show()
                            finish()

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

    }

    fun onClickListeners(){

        binding.btnLogin.setOnClickListener {
          viewModel.userLoggedIn()
        }


        binding.btnRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}