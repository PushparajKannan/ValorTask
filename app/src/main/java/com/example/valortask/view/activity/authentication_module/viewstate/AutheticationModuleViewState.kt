package com.example.valortask.view.activity.authentication_module.viewstate

import com.example.valortask.baseclass.BaseViewState



sealed class RegisterViewState : BaseViewState  {
    object Reset : RegisterViewState()
    data class UserRegisterSuccessFully(var msg : String) : RegisterViewState()
}


sealed class LoginViewState : BaseViewState  {
    object Reset : LoginViewState()
    data class UserLoggedInSuccessFully(var msg : String) : LoginViewState()
}