package com.example.valortask.repository

import com.example.valortask.network.WebServices
import com.example.valortask.repository.ApiDataRepository.ApiError.Companion.EMPTY_API_ERROR
import com.example.valortask.utilities.AuthState
import com.example.valortask.view.activity.users_module.model.ProductListAPIModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val apiLists: WebServices
) {


    fun apiFetchProductList(id: String) =
        flow<AuthState<List<ProductListAPIModel>?>> {

            //  if (internetConnection.value == true) {
            emit(AuthState.loading())

            val apiCall = apiLists.apiProductList(id)

            if (apiCall.isSuccessful) {
                emit(AuthState.authenticated(apiCall.body()))
            } else {
                throw HttpException(apiCall)
            }
            /*} else {
                emit(AuthState.authenticationFailed("No Internet Connection"))
            }*/
        }.catch {

            emit(AuthState.authenticationFailed(it.getApiError()?.result ?: ""))
        }.flowOn(Dispatchers.IO)


    /**
     * When 400 Error Response
     */

    data class ApiError(
        val code: Int,
        @SerializedName("response") var result: String? = "",
        @SerializedName("message") var message: String? = ""
    ) {
        companion object {
            val EMPTY_API_ERROR = ApiError(-1, "", "")
        }
    }


    fun Throwable.getApiError(): ApiError? {
        if (this is HttpException) {
            try {
                val errorJsonString = this.response()?.errorBody()?.string()

                val error = Gson().fromJson(errorJsonString, ApiError::class.java)
                return ApiError(400, error.result, error.message)
            } catch (exception: Exception) {
                // Ignore
                return EMPTY_API_ERROR
            }
        }
        return EMPTY_API_ERROR
    }

}