package com.example.valortask.database.tablemodel

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ProductsDbModel(
    @PrimaryKey
    var productId : Int  ,
    var productName : String? = "",
    var productImage : String? = "",
    var productDescription : String? = "",
    var productPrice : Double?,
    var productQuantity : Int? = 1
)