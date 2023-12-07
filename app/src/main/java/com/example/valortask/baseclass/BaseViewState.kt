package com.example.valortask.baseclass

interface  BaseViewState {
    object Init : BaseViewState
    object ShowLoading : BaseViewState
    object DismissLoading : BaseViewState
    object UserLogOut : BaseViewState
    data class ShowError(val msg: String?) : BaseViewState
}