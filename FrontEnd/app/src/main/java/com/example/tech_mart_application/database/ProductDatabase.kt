package com.example.tech_mart_application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tech_mart_application.models.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: ProductDatabase? = null
        private val DATABASE_NAME = "product.db"

        @Synchronized
        fun getInstance(context: Context): ProductDatabase {
            if (INSTANCE == null) {
                synchronized(ProductDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ProductDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun productWishlistDAO(): ProductDAO
}