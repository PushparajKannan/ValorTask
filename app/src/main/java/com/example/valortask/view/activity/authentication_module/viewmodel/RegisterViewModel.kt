package com.example.valortask.view.activity.authentication_module.viewmodel

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.database.tablemodel.UserListDbModel
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.utilities.ValidationUtils
import com.example.valortask.view.activity.authentication_module.viewstate.RegisterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository
) : BaseViewModel(){


    val userName = MutableLiveData<String>("")
    val userMail = MutableLiveData<String>("")
    val userPassword = MutableLiveData<String>("")
    val userConformPassword = MutableLiveData<String>("")
    val userPhone = MutableLiveData<String>("")
    val userDOB = MutableLiveData<String>("")



    fun registerUser(){
        when{
            (userName.value.isNullOrEmpty() ||
                    userMail.value.isNullOrEmpty() ||
                    userPassword.value.isNullOrEmpty() ||
                    userConformPassword.value.isNullOrEmpty() ||
                    userPhone.value.isNullOrEmpty() ||
                    userDOB.value.isNullOrEmpty()) -> {

                updateUiState(BaseViewState.ShowError("Enter all details"))

            }

            ValidationUtils.isValidEmailPatterMatcher(userMail.value).not() ->{
                updateUiState(BaseViewState.ShowError("Enter valid email address"))

            }

            ValidationUtils.isValidPhoneNumber(userPhone.value).not() ->{
                updateUiState(BaseViewState.ShowError("Phone number must be 10 digits"))

            }

            ValidationUtils.isPasswordValid(userPassword.value).not() ->{
                updateUiState(BaseViewState.ShowError("Enter valid password"))

            }

            (userPassword.value != userConformPassword.value) ->{
                updateUiState(BaseViewState.ShowError("Password and Conform password are not same"))
            }

            (userName.value.isNullOrEmpty().not() &&
                    userMail.value.isNullOrEmpty().not() &&
                    userPassword.value.isNullOrEmpty().not() &&
                    userConformPassword.value.isNullOrEmpty().not() &&
                    userPhone.value.isNullOrEmpty().not() &&
                    userDOB.value.isNullOrEmpty().not()) -> {



                createNewUser()


            }

            else->{

            }



        }
    }


    fun createNewUser(){
        viewModelScope.launch(Dispatchers.IO) {
            userPhone.value?.let {
           val isExistingUser = localDbRepo.isExistingUser(it)



                if(isExistingUser.not()){
                    val encodedPassword: String = Base64.encodeToString(userPassword.value?.encodeToByteArray(), Base64.DEFAULT)

                    val model = UserListDbModel(
                        phoneNumber= it,
                        userName = userName.value,
                        userMail= userMail.value,
                        userDateOfBirth= userDOB.value,
                        userPassword= encodedPassword

                    )

                    localDbRepo.insertNewUser(model)
                    updateUiState(RegisterViewState.UserRegisterSuccessFully("Account Created Successfully."))

                }else{
                    updateUiState(BaseViewState.ShowError("Account Already Exist"))

                }




         }



        }
    }


   // fun resetViewState() = updateUiState(BaseViewState.Init)


}