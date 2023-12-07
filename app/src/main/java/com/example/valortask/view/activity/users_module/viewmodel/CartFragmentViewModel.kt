package com.example.valortask.view.activity.users_module.viewmodel

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.database.tablemodel.ProductsDbModel
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.view.activity.users_module.model.ProductList
import com.example.valortask.view.activity.users_module.model.convertProductDbModelToProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository
) : BaseViewModel() {

    val productListData = liveData<List<ProductList>> {
        emit(emptyList())
        localDbRepo.fetchCartProduct().collectLatest {
        it?.let {
            emit(it.convertProductDbModelToProductModel())
        }
        }
    }




    fun updateProduct(qty : Int,id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepo.updateCartProductQty(qty, id)
        }
    }

    fun deleteCartProduct(model : ProductsDbModel){
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepo.deleteCartProduct(model)
        }
    }
}