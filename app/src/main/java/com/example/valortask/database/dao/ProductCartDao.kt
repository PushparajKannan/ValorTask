package com.example.valortask.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.valortask.database.tablemodel.ProductsDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface ProductCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardList(config: List<ProductsDbModel>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCartProduct(vararg config: ProductsDbModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(vararg config: ProductsDbModel?)

    @Query("UPDATE ProductsDbModel set productQuantity =:qty WHERE productId=:id")
    abstract fun updateQty(qty: Int, id: Int)


    @Delete
    suspend fun deleteCartProduct(vararg config: ProductsDbModel?)

    @Query("SELECT * FROM ProductsDbModel ORDER BY productName DESC")
    abstract fun loadCartProductList(): Flow<List<ProductsDbModel>?>

    fun getCartProductListUpdate() =
        loadCartProductList().distinctUntilChanged()

    @Query("DELETE FROM ProductsDbModel")
    abstract fun deleteAllEntities()
}