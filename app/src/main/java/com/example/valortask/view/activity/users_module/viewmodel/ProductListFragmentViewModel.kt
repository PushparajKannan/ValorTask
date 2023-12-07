package com.example.valortask.view.activity.users_module.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.database.tablemodel.ProductsDbModel
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.utilities.AuthState
import com.example.valortask.view.activity.users_module.model.ProductList
import com.example.valortask.view.activity.users_module.model.convertToProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListFragmentViewModel @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository
) : BaseViewModel() {

    val productListData = MutableLiveData<List<ProductList>>()
    val isFetchedProductData = MutableLiveData<Boolean>(true)


    fun fetchProductList(){

        viewModelScope.launch {

            networkRepo.apiFetchProductList("821a00e7-c125-4b0c-977a-6077f5fc932a").collect { _authState->
                when (_authState) {
                    is AuthState.Loading -> {
                        showLoading()

                    }
                    is AuthState.Authenticated -> {
                        dismissLoading()
                        isFetchedProductData.postValue(false)

                        _authState.data?.let {
                            productListData.postValue(it.convertToProductModel())
                        }
                    }

                    is AuthState.AuthenticationFailed -> {
                        dismissLoading()
                    }
                }

            }

        }


    }


    fun insertCart(model: ProductsDbModel){
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepo.insertCartProduct(model)
        }

    }

}