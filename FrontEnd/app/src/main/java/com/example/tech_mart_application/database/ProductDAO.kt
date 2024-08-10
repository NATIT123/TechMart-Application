package com.example.tech_mart_application.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tech_mart_application.models.Product

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product): Long

    @Query("SELECT * FROM products")
    fun getListProduct(): LiveData<List<Product>>

    @Delete
    suspend fun deleteProduct(product: Product)
}