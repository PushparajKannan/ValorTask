package com.example.valortask.network


import com.example.valortask.view.activity.users_module.model.ProductListAPIModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServices {


    /**
     * Coroutines API Call
     * Use Suspend KEY Word
     * **/

    @GET("{id}")
    suspend fun apiProductList(@Path("id") id: String): Response<List<ProductListAPIModel>>

}