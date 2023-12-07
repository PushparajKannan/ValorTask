package com.example.valortask.view.activity.authentication_module.viewmodel

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.utilities.SharedPref
import com.example.valortask.view.activity.authentication_module.viewstate.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository,
    private val sharedPref: SharedPref
) : BaseViewModel() {

    val userPassword = MutableLiveData<String>("")
    val userPhone = MutableLiveData<String>("")



    fun userLoggedIn(){
        when{

            (userPhone.value.isNullOrEmpty() || userPassword.value.isNullOrEmpty()) -> {
                updateUiState(BaseViewState.ShowError("Phone number or Password is empty"))
            }

            (userPhone.value.isNullOrEmpty().not() && userPassword.value.isNullOrEmpty().not()) -> {
                authenticateUser()
            }



            else ->{
                updateUiState(BaseViewState.ShowError("Please enter valid credentials"))

            }
        }
    }

    fun authenticateUser(){
        viewModelScope.launch(Dispatchers.IO) {

            userPhone.value?.let {_userPhone->

                userPassword.value?.let { _userPassword ->

                    val encodedPassword: String = Base64.encodeToString(_userPassword.encodeToByteArray(), Base64.DEFAULT)

                    val authenticated = localDbRepo.authenticateUser(_userPhone,encodedPassword)

                    if(authenticated){

                        sharedPref.loggedInUserNumber = _userPhone
                        sharedPref.isUerLoggedIn = true
                        updateUiState(LoginViewState.UserLoggedInSuccessFully("Welcome"))

                    }else{
                        updateUiState(BaseViewState.ShowError("Invalid account credentials"))

                    }

                }

            }


        }
    }

   // fun resetViewState() = updateUiState(BaseViewState.Init)


}