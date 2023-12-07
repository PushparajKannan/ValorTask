package com.example.valortask.view.activity.users_module.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.database.tablemodel.UserListDbModel
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.utilities.SharedPref
import com.example.valortask.view.activity.users_module.model.ProductList
import com.example.valortask.view.activity.users_module.model.SavedCardModel
import com.example.valortask.view.activity.users_module.model.convertProductDbModelToProductModel
import com.example.valortask.view.activity.users_module.viewstate.PaymentType
import com.example.valortask.view.activity.users_module.viewstate.PaymentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeActivityViewModel @Inject constructor(private val networkRepo: ApiDataRepository,
                                                private val localDbRepo: LocalDataRepository,
                                                private val sharedPref: SharedPref
) : BaseViewModel(){

    var stateTax = MutableLiveData<String>("0")
    var customFee = MutableLiveData<String>("0")

    var paymentType = MutableLiveData<PaymentType>(PaymentType.CARD)
    var selectedCard = MutableLiveData<SavedCardModel?>(null)

    var cashAmount = MutableLiveData<String>()
    var totalAmount = MutableLiveData<Double>()


    val loggedInUserInfo = liveData<UserListDbModel?> {

        emit(null)
        sharedPref.loggedInUserNumber?.let { _userPhoneNumber ->
            localDbRepo.fetchUser(_userPhoneNumber).collectLatest {
                emit(it)
            }
        }
    }


    var cardList = MutableLiveData<List<SavedCardModel>>(
        mutableListOf(
            SavedCardModel(1,"Pushparaj","05/25","0983"),
            SavedCardModel(2,"Kannan","04/26","1345"),
            SavedCardModel(3,"Raj","03/27","3213"),
            SavedCardModel(4,"Vignesh","02/24","4234"),
            SavedCardModel(5,"Karthi","05/26","5645"),
            SavedCardModel(6,"Kumar","10/25","9045"),
            SavedCardModel(6,"Muthu","11/24","8974"),
        )
    )



    val cartProductListData = liveData<List<ProductList>> {
        emit(emptyList())
        localDbRepo.fetchCartProduct().collectLatest {
            it?.let {
                emit(it.convertProductDbModelToProductModel())
            }
        }
    }


    fun saveSetting(){
        sharedPref.stateTax = if(stateTax.value.isNullOrEmpty()) "0" else (stateTax.value ?: "0")
        sharedPref.customFee = if(customFee.value.isNullOrEmpty()) "0" else (customFee.value ?: "0")
    }

    fun getSettingsData(){
        stateTax.postValue(sharedPref.stateTax)
        customFee.postValue(sharedPref.customFee)
    }


    fun updateSelectedCard(model : SavedCardModel){
        selectedCard.value = model
    }

    fun placeOrder(){

        when(paymentType.value){
            PaymentType.CASH ->{

                when{
                    cashAmount.value.isNullOrEmpty() ->{
                        updateUiState(BaseViewState.ShowError("Please Enter Amount"))
                    }
                    ((cashAmount.value?.toDouble() ?: 0.0) < (totalAmount.value?.toDouble() ?: 0.0)) ->{
                        updateUiState(BaseViewState.ShowError("Insufficient Amount"))
                    }

                    ((cashAmount.value?.toDouble() ?: 0.0) > (totalAmount.value?.toDouble() ?: 0.0)) ->{
                        updateUiState(BaseViewState.ShowError("More the total amount"))
                    }

                    ((cashAmount.value?.toDouble() ?: 0.0) == (totalAmount.value?.toDouble() ?: 0.0)) ->{
                        updateUiState(PaymentViewState.PaymentStatus("Payment Success and Order Placed"))
                        resetPayment()
                    }
                }

            }

            PaymentType.CARD ->{

               selectedCard.value?.let {
                   updateUiState(PaymentViewState.PaymentStatus("Payment Success and Order Placed"))
                   resetPayment()

               } ?: kotlin.run {
                   updateUiState(BaseViewState.ShowError("Select Payment Card"))

               }

            }
        }


    }

    fun deleteCardProduct(){
        viewModelScope.launch {
            localDbRepo.deleteAll()
        }
    }

    fun resetPayment(){
        deleteCardProduct()
        cashAmount.value = ""
        selectedCard.value = null
        paymentType.value = PaymentType.CARD
    }

    fun userLogOut(){
        sharedPref.clear()
        deleteCardProduct()
        updateUiState(BaseViewState.UserLogOut)
    }



}