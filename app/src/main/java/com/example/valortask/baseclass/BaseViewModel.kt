package com.example.valortask.baseclass

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


abstract class BaseViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<BaseViewState?> = MutableStateFlow(BaseViewState.Init)
    val uiState: StateFlow<BaseViewState?> = _uiState.asStateFlow()



    fun showLoading() = _uiState.update { BaseViewState.ShowLoading }

    fun dismissLoading() = _uiState.update { BaseViewState.DismissLoading }



    fun showError(msg: String?) = _uiState.update {
        BaseViewState.ShowError(msg)
    }


    protected fun updateUiState(state: BaseViewState) = _uiState.update { state }

    fun resetViewState() = updateUiState(BaseViewState.Init)

}