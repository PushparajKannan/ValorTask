package com.example.valortask.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.valortask.database.tablemodel.UserListDbModel
import kotlinx.coroutines.flow.Flow


@Dao
interface UserListDao {


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(vararg model : UserListDbModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg config: UserListDbModel?)

    @Query("SELECT EXISTS(SELECT * FROM UserListDbModel WHERE phoneNumber = :number)")
    fun isUserExist(number : String) : Boolean

    @Query("SELECT EXISTS(SELECT * FROM UserListDbModel WHERE phoneNumber = :number AND userPassword = :password)")
    fun authenticateUser(number : String,password : String) : Boolean


    @Query("SELECT * FROM UserListDbModel WHERE phoneNumber =:number")
    fun fetchUser(number : String) : UserListDbModel?

    @Query("SELECT * FROM UserListDbModel WHERE phoneNumber =:number")
    fun fetchUserFlow(number : String) : Flow<UserListDbModel?>


    @Delete
    suspend fun deleteUser(vararg config: UserListDbModel?)

    /*@Query("SELECT * FROM UserListDbModel  ORDER BY name DESC")
    abstract fun loadRoomList(): Flow<List<UserListDbModel>?>

    fun getRoomsListUpdate() =
        loadRoomList().distinctUntilChanged()*/
}