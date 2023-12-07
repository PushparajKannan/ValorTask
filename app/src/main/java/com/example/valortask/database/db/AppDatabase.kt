package com.example.valortask.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.valortask.database.dao.ProductCartDao
import com.example.valortask.database.dao.UserListDao
import com.example.valortask.database.tablemodel.ProductsDbModel
import com.example.valortask.database.tablemodel.UserListDbModel

@Database(
    entities = [UserListDbModel::class,ProductsDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userListDao(): UserListDao
    abstract fun cartProductDao(): ProductCartDao

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "valorTask"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance
        }
    }
}