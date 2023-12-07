package com.example.valortask.view.activity.users_module.model

import com.example.valortask.database.tablemodel.ProductsDbModel
import com.google.gson.annotations.SerializedName

class ProductListDataModel {
}

data class ProductList(
    var productId : Int? = null ,
    var productName : String? = "",
    var productImage : String? = "",
    var productDescription : String? = "",
    var productPrice : Double? ,
    var productQuantity : Int? = 1
    )

data class ProductListAPIModel(
    @SerializedName("id")
    var id : Int ,
    @SerializedName("title")
    var title : String ,
    @SerializedName("price")
    var price : Double ,
    @SerializedName("description")
    var description : String ,
    @SerializedName("category")
    var category : String ,
    @SerializedName("image")
    var image : String ,
)

fun List<ProductListAPIModel>.convertToProductModel() : List<ProductList>{

  return  this.map {
        ProductList(
            it.id,
            it.category,
            it.image,
            it.title,
            it.price,
            1
        )
    }
}


fun List<ProductsDbModel>.convertProductDbModelToProductModel() : List<ProductList>{

    return  this.map {
        ProductList(
            it.productId,
            it.productName,
            it.productImage,
            it.productDescription,
            it.productPrice,
            it.productQuantity
        )
    }
}


fun ProductList.convertToProductDbModel() : ProductsDbModel{

    return ProductsDbModel(
        this.productId ?: 1,
        this.productName,
        this.productImage,
        this.productDescription,
        this.productPrice,
        this.productQuantity
    )
}

fun ProductList.convertToProductDbModelWithQty(qty : Int) : ProductsDbModel{

    return ProductsDbModel(
        qty,
        this.productName,
        this.productImage,
        this.productDescription,
        this.productPrice,
        this.productQuantity
    )
}