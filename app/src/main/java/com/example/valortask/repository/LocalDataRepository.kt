package com.example.valortask.repository

import com.example.valortask.database.db.AppDatabase
import com.example.valortask.database.tablemodel.ProductsDbModel
import com.example.valortask.database.tablemodel.UserListDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataRepository @Inject constructor(val database: AppDatabase) {

    /**
     * Users Db CURD OPERATION FROM LOCAL DATA BASE
     */


    suspend fun insertNewUser(data: UserListDbModel) = withContext(Dispatchers.IO) {
        database.userListDao().insertUser(data)
    }

    suspend fun isExistingUser(phoneNumber : String) = withContext(Dispatchers.IO) {
        database.userListDao().isUserExist(phoneNumber)
    }

    suspend fun authenticateUser(phoneNumber : String, password : String) = withContext(Dispatchers.IO) {
        database.userListDao().authenticateUser(phoneNumber,password)
    }


    suspend fun fetchUser(phoneNumber : String) = withContext(Dispatchers.IO) {
        database.userListDao().fetchUserFlow(phoneNumber)
    }

    suspend fun updateUser(model : UserListDbModel) = withContext(Dispatchers.IO) {
        database.userListDao().updateUser(model)
    }


    /**
     * Products DB CURD OPERATION FROM LOCAL DATA BASE
     */



    suspend fun insertCartProduct(data: ProductsDbModel) = withContext(Dispatchers.IO) {
        database.cartProductDao().insertCartProduct(data)
    }

    suspend fun updateCartProduct(data: ProductsDbModel) = withContext(Dispatchers.IO) {
        database.cartProductDao().updateCartProduct(data)
    }

    suspend fun updateCartProductQty(qty: Int, id: Int) = withContext(Dispatchers.IO) {
        database.cartProductDao().updateQty(qty, id)
    }

    suspend fun deleteCartProduct(data: ProductsDbModel) = withContext(Dispatchers.IO) {
        database.cartProductDao().deleteCartProduct(data)
    }



    suspend fun fetchCartProduct() = withContext(Dispatchers.IO) {
        database.cartProductDao().loadCartProductList()
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO){
        database.cartProductDao().deleteAllEntities()
    }

}