package com.example.valortask.view.activity.users_module.viewstate

import com.example.valortask.baseclass.BaseViewState

class UserModelViewState {
}

sealed class ProfileViewState : BaseViewState {
    object Reset : ProfileViewState()
    data class ProfileUpdated(var msg : String) : ProfileViewState()
}


sealed class PaymentViewState : BaseViewState {
    object Reset : PaymentViewState()
    data class PaymentStatus(var msg : String) : PaymentViewState()
}





//enum class ProfileViewType

enum class ProfileViewType(val value: Int){
    EDIT(1),
    VIEW(2);

    companion object {
        fun fromInt(value: Int) = ProfileViewType.values().first { it.value == value }
    }
}


enum class PaymentType(val value: Int){
    CASH(1),
    CARD(2);

    companion object {
        fun fromInt(value: Int) = PaymentType.values().first { it.value == value }
    }
}